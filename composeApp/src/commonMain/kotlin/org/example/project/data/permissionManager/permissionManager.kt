package org.example.project.data.permissionManager

interface PermissionResultCallback {
    fun onPermissionGranted()
    fun onPermissionDenied(isPermanentDenied: Boolean)
}

enum class AppPermission {
    CAMERA, GALLERY
}

expect interface PermissionBridgeListener {
    fun requestPermission(permission: AppPermission, callback: PermissionResultCallback)
    fun isPermissionGranted(permission: AppPermission): Boolean
}

class PermissionBridge {
    private var listener: PermissionBridgeListener? = null

    fun setListener(listener: PermissionBridgeListener) {
        this.listener = listener
    }

    fun requestPermission(permission: AppPermission, callback: PermissionResultCallback) {
        listener?.requestPermission(permission, callback)
            ?: error("PermissionBridgeListener")
    }

    fun isPermissionGranted(permission: AppPermission): Boolean {
        return listener?.isPermissionGranted(permission) ?: false
    }
}