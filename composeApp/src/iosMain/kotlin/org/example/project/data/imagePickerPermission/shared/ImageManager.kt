@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package org.example.project.data.imagePickerPermission.shared

import androidx.compose.runtime.Composable
import kotlinx.cinterop.*
import platform.AVFoundation.*
import platform.Foundation.*
import platform.PhotosUI.*
import platform.UIKit.*
import platform.darwin.*
import platform.UIKit.UIImagePickerControllerSourceType
import kotlinx.cinterop.usePinned
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePNGRepresentation

private fun NSData.asByteArray(): ByteArray {
    val out = ByteArray(length.toInt())
    out.usePinned { pinned ->
        // copies 'length' bytes into out
        this.getBytes(pinned.addressOf(0), length)
    }
    return out
}


private fun topViewController(): UIViewController? {
    val app = UIApplication.sharedApplication
    val window: UIWindow? =
        app.keyWindow
            ?: ((app.windows as? List<*>)?.firstOrNull() as? UIWindow)
    var top: UIViewController? = window?.rootViewController
    while (true) {
        val presented = top?.presentedViewController ?: break
        top = presented
    }
    return top
}

/*
interface ImageManager {
    fun launchGallery()
    fun launchCamera()
}

 */

@Composable
actual fun createImageManager(onResult: (ByteArray?) -> Unit): ImageManager =
    IOSImageManager(onResult)


private class IOSImageManager(
    private val onResult: (ByteArray?) -> Unit
) :  ImageManager {

    override fun launchGallery() {
        dispatch_async(dispatch_get_main_queue()) {
            val presenter = topViewController() ?: return@dispatch_async

            val cfg = PHPickerConfiguration().apply {
                setSelectionLimit(1)
                setFilter(PHPickerFilter.imagesFilter())
            }
            val picker = PHPickerViewController(configuration = cfg)

            picker.setDelegate(object : NSObject(), PHPickerViewControllerDelegateProtocol {
                override fun picker(
                    picker: PHPickerViewController,
                    didFinishPicking: List<*>
                ) {
                    picker.dismissViewControllerAnimated(true, completion = null)

                    val first = didFinishPicking.firstOrNull() as? PHPickerResult
                        ?: return onResult(null)

                    val provider = first.itemProvider
                    provider.loadDataRepresentationForTypeIdentifier("public.image") { data, _ ->
                        onResult(data?.asByteArray())
                    }
                }
            })

            presenter.presentViewController(picker, animated = true, completion = null)
        }
    }

    override fun launchCamera() {
        dispatch_async(dispatch_get_main_queue()) {
            val presenter = topViewController() ?: return@dispatch_async

            when (AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)) {
                AVAuthorizationStatusAuthorized -> presentCamera(presenter)
                AVAuthorizationStatusNotDetermined ->
                    AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                        if (granted) presentCamera(presenter) else onResult(null)
                    }
                else -> onResult(null)
            }
        }
    }
    private fun presentCamera(presenter: UIViewController) {
        val cameraSource: UIImagePickerControllerSourceType =
            UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera

        if (!UIImagePickerController.isSourceTypeAvailable(cameraSource)) {
            onResult(null); return
        }

        val picker = UIImagePickerController().apply {
            sourceType = cameraSource
            allowsEditing = false
        }

        picker.setDelegate(object : NSObject(),
            UIImagePickerControllerDelegateProtocol,
            UINavigationControllerDelegateProtocol {

            override fun imagePickerController(
                picker: UIImagePickerController,
                didFinishPickingMediaWithInfo: Map<Any?, *>
            ) {
                picker.dismissViewControllerAnimated(true, completion = null)

                val uiImage = didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
                val data = uiImage?.let { UIImageJPEGRepresentation(it, 0.9) }
                    ?: uiImage?.let { UIImagePNGRepresentation(it) }

                onResult(data?.asByteArray())
            }

            override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                picker.dismissViewControllerAnimated(true, completion = null)
                onResult(null)
            }
        })

        presenter.presentViewController(picker, animated = true, completion = null)
    }

}
