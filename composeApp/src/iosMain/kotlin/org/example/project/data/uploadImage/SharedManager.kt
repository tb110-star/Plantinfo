package org.example.project.data.uploadImage


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.project.data.uploadImage.uploadImageRepository.saveImageToDocuments
import platform.Foundation.NSURL
import platform.UIKit.*
import platform.darwin.NSObject


// CameraManager.ios.kt
@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
    val imagePicker = UIImagePickerController()
    val cameraDelegate = remember {
        object : NSObject(), UIImagePickerControllerDelegateProtocol,
            UINavigationControllerDelegateProtocol {
            override fun imagePickerController(
                picker: UIImagePickerController, didFinishPickingMediaWithInfo: Map<Any?, *>
            ) {
                val image =
                    didFinishPickingMediaWithInfo.getValue(UIImagePickerControllerEditedImage) as? UIImage
                        ?: didFinishPickingMediaWithInfo.getValue(
                            UIImagePickerControllerOriginalImage
                        ) as? UIImage
              //  onResult.invoke(SharedImage(image))
                val localPath = image?.let { saveImageToDocuments(it) }
                onResult.invoke(
                    if (localPath != null) SharedImage(localPath = localPath)
                    else null
                )
                picker.dismissViewControllerAnimated(true, null)
            }
        }
    }
    return remember {
        CameraManager {
            imagePicker.setSourceType(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera)
            imagePicker.setAllowsEditing(true)
            imagePicker.setCameraCaptureMode(UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto)
            imagePicker.setDelegate(cameraDelegate)
            UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                imagePicker, true, null
            )
        }
    }
}

actual class CameraManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}

// GalleryManager.ios.kt
@Composable
actual fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager {
    val imagePicker = UIImagePickerController()
    val galleryDelegate = remember {
        object : NSObject(), UIImagePickerControllerDelegateProtocol,
            UINavigationControllerDelegateProtocol {
            override fun imagePickerController(
                picker: UIImagePickerController, didFinishPickingMediaWithInfo: Map<Any?, *>
            ) {
                val image = didFinishPickingMediaWithInfo.getValue(
                    UIImagePickerControllerEditedImage
                ) as? UIImage ?: didFinishPickingMediaWithInfo.getValue(
                    UIImagePickerControllerOriginalImage
                ) as? UIImage
               // onResult.invoke(SharedImage(image))
                val localPath = image?.let { saveImageToDocuments(it) }
                onResult.invoke(
                    if (localPath != null) SharedImage(localPath = localPath)
                    else null
                )
                picker.dismissViewControllerAnimated(true, null)
            }
        }
    }

    return remember {
        GalleryManager {
            imagePicker.setSourceType(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary)
            imagePicker.setAllowsEditing(true)
            imagePicker.setDelegate(galleryDelegate)
            UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                imagePicker, true, null
            )
        }
    }
}

actual class GalleryManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}
