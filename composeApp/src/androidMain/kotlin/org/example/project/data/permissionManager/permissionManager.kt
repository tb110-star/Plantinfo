package org.example.project.data.permissionManager

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

actual interface PermissionBridgeListener {
    actual fun requestPermission(
        permission: AppPermission,
        callback: PermissionResultCallback
    )
    actual fun isPermissionGranted(permission: AppPermission): Boolean
}


class AndroidPermissionBridgeListener(
    private val activity: ComponentActivity
) : PermissionBridgeListener {
    override fun requestPermission(permission: AppPermission, callback: PermissionResultCallback) {
        val androidPerm = when (permission) {
            AppPermission.CAMERA -> Manifest.permission.CAMERA
            AppPermission.GALLERY -> Manifest.permission.READ_EXTERNAL_STORAGE
        }
        if (ContextCompat.checkSelfPermission(activity, androidPerm) == PackageManager.PERMISSION_GRANTED) {
            callback.onPermissionGranted()
        } else {
            callback.onPermissionDenied(false)
        }
    }

    override fun isPermissionGranted(permission: AppPermission): Boolean {
        val androidPerm = when (permission) {
            AppPermission.CAMERA -> Manifest.permission.CAMERA
            AppPermission.GALLERY -> Manifest.permission.READ_EXTERNAL_STORAGE
        }
        return ContextCompat.checkSelfPermission(activity, androidPerm) == PackageManager.PERMISSION_GRANTED
    }
}