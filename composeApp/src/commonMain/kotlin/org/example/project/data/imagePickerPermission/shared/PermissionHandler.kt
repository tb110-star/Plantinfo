package org.example.project.data.imagePickerPermission.shared

import androidx.compose.runtime.Composable

interface PermissionHandler {
    @Composable
    fun askPermission(permission: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun launchSettings()

}