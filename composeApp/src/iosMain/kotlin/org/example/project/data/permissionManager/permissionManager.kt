

package org.example.project.data.permissionManager
actual interface PermissionBridgeListener {
    actual fun requestPermission(permission: AppPermission, callback: PermissionResultCallback)
    actual fun isPermissionGranted(permission: AppPermission): Boolean
}

// later add native codes