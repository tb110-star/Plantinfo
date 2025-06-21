package org.example.project.data.uploadImage

import android.content.ContentResolver
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext


@Composable
actual fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        onResult(uri?.let { SharedImage(localPath = it.toString()) })
    }

    return remember {
        GalleryManager(
            onLaunch = {
                galleryLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        )
    }
}

actual class GalleryManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}
@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
    val context = LocalContext.current
    var tempPhotoUri by remember { mutableStateOf(Uri.EMPTY) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempPhotoUri != Uri.EMPTY) {
            onResult(SharedImage(localPath = tempPhotoUri.toString()))
        } else {
            onResult(null)
        }
    }

    return remember {
        CameraManager(
            onLaunch = {
                tempPhotoUri = ComposeFileProvider.getImageUri(context)
                cameraLauncher.launch(tempPhotoUri)
            }
        )
    }
}

actual class CameraManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}