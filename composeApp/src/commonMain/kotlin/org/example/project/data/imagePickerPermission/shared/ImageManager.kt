package org.example.project.data.imagePickerPermission.shared

import androidx.compose.runtime.Composable


interface ImageManager {
    fun launchCamera()
    fun launchGallery()
}
@Composable

expect fun createImageManager(onResult: (ByteArray?) -> Unit): ImageManager
