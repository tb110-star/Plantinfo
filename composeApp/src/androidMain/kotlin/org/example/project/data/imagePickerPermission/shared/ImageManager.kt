package org.example.project.data.imagePickerPermission.shared

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import coil.imageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import kmp.image.picker.ImageUriHelper


class AndroidImageManager(
    private val cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
    private val galleryLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    private val getImageUri: () -> Uri
) : ImageManager {
    override fun launchCamera() {
        cameraLauncher.launch(getImageUri())
    }

    override fun launchGallery() {
        galleryLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }
}

@Composable
actual fun createImageManager(onResult: (ByteArray?) -> Unit): ImageManager {
    val context = LocalContext.current
  //  val contentResolver = context.contentResolver
    val scope = rememberCoroutineScope()

    var tempPhotoUri by remember { mutableStateOf(Uri.EMPTY) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            scope.loadAndReturnImage(tempPhotoUri, context, onResult)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            scope.loadAndReturnImage(it, context, onResult)
        }
    }

    return remember {
        AndroidImageManager(
            cameraLauncher = cameraLauncher,
            galleryLauncher = galleryLauncher,
            getImageUri = {
                ImageUriHelper.createTempImageUri(context).also { tempPhotoUri = it }
            }
        )
    }
}

private fun CoroutineScope.loadAndReturnImage(
    uri: Uri,
    context: Context,
    onResult: (ByteArray?) -> Unit
) {
    launch(Dispatchers.IO) {
        try {
            val loader = context.imageLoader
            val request = ImageRequest.Builder(context)
                .data(uri)
                .allowHardware(false)
                .build()
            val result = loader.execute(request).drawable
            val bitmap = (result as? BitmapDrawable)?.bitmap
            val byteArray = bitmap?.let { bmp ->
                val output = ByteArrayOutputStream()
                bmp.compress(Bitmap.CompressFormat.JPEG, 85, output)
                output.toByteArray()
            }
            onResult(byteArray)
        } catch (e: Exception) {
            e.printStackTrace()
            onResult(null)
        }
    }
}
