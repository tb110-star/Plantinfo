package org.example.project.data.uploadImage

import androidx.compose.runtime.Composable

// CameraManager.kt
@Composable
expect fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager

expect class CameraManager(
    onLaunch: () -> Unit
) {
    fun launch()
}

// GalleryManager.kt
@Composable
expect fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager

expect class GalleryManager(
    onLaunch: () -> Unit
) {
    fun launch()
}
