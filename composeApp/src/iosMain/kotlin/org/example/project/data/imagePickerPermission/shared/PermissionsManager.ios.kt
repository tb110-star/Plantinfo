/*package org.example.project.data.imagePickerPermission.shared

import androidx.compose.runtime.Composable

actual class PermissionsManager actual constructor(
    private val callback: PermissionCallback
) : PermissionHandler {

    init {
        println("✅ iOS PermissionsManager initialized.")
    }

    @Composable
    override fun askPermission(permission: PermissionType) {
        println("🚫 iOS does not support runtime permissions in Compose yet. Granting by default: $permission")
        callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
    }

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        println("✅ iOS permission assumed granted for $permission")
        return true
    }

    @Composable
    override fun launchSettings() {
        println("🔧 iOS does not support opening settings directly from Compose. Please add native logic if needed.")
    }
}

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return PermissionsManager(callback)
}


 */
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package org.example.project.data.imagePickerPermission.shared

import androidx.compose.runtime.Composable
import platform.AVFoundation.*
import platform.Foundation.NSURL
import platform.Photos.*
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return PermissionsManager(callback)
}

actual class PermissionsManager actual constructor(
    private val callback: PermissionCallback
) : PermissionHandler {

    @Composable
    override fun askPermission(permission: PermissionType) {
        when (permission) {
            PermissionType.CAMERA -> {
                val status = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
                when (status) {
                    AVAuthorizationStatusAuthorized -> {
                        callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                    }
                    AVAuthorizationStatusNotDetermined -> {
                        AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                            if (granted) {
                                callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                            } else {
                                callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                            }
                        }
                    }
                    AVAuthorizationStatusDenied,
                    AVAuthorizationStatusRestricted -> {
                        callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                    }
                    else -> {
                        callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                    }
                }
            }

            PermissionType.GALLERY -> {
                val status = PHPhotoLibrary.authorizationStatus()
                when (status) {
                    PHAuthorizationStatusAuthorized,
                    PHAuthorizationStatusLimited -> {
                        callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                    }
                    PHAuthorizationStatusNotDetermined -> {
                        PHPhotoLibrary.requestAuthorization { newStatus ->
                            when (newStatus) {
                                PHAuthorizationStatusAuthorized,
                                PHAuthorizationStatusLimited ->
                                    callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                                PHAuthorizationStatusDenied,
                                PHAuthorizationStatusRestricted ->
                                    callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                                else ->
                                    callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                            }
                        }
                    }
                    PHAuthorizationStatusDenied,
                    PHAuthorizationStatusRestricted -> {
                        callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                    }
                    else -> callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                }
            }
        }
    }

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.CAMERA ->
                AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo) ==
                        AVAuthorizationStatusAuthorized

            PermissionType.GALLERY -> {
                val s = PHPhotoLibrary.authorizationStatus()
                s == PHAuthorizationStatusAuthorized || s == PHAuthorizationStatusLimited
            }
        }
    }

    @Composable
    override fun launchSettings() {
        NSURL.URLWithString(UIApplicationOpenSettingsURLString)?.let { url ->
            UIApplication.sharedApplication.openURL(url)
        }
    }
}
