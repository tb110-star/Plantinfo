package org.example.project.data.uploadImage

import AddImageSheetScreen
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import org.example.project.ui.viewModels.UploadImageViewModel

// androidMain
@Composable
fun AddImageSheetScreenAndroid(
    viewModel: UploadImageViewModel,
    onCloseClick: () -> Unit
) {
    val context = LocalContext.current
    val launcherGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                viewModel.setImage(
                    SharedImage(
                        localPath = uri.toString(),
                    )
                )
            }
        }
    )
    val launcherCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap: Bitmap? ->

        }
    )

    AddImageSheetScreen(
        onGalleryClick = { launcherGallery.launch("image/*") },
        onCameraClick = { launcherCamera.launch(null) },
        onSendClick = { /* ... */ },
        selectedImagePath = viewModel.image.collectAsState().value?.localPath,
        onCloseClick = onCloseClick
    )
}
