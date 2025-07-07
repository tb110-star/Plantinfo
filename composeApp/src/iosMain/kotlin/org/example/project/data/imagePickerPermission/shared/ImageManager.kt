package org.example.project.data.imagePickerPermission.shared

import androidx.compose.runtime.Composable

@Composable

actual fun createImageManager(onResult: (ByteArray?) -> Unit): ImageManager {
    return object : ImageManager {
        override fun launchCamera() {
            println("iOS camera not implemented yet")
            onResult(null)
        }

        override fun launchGallery() {
            println("iOS gallery not implemented yet")
            onResult(null)
        }
    }
}
