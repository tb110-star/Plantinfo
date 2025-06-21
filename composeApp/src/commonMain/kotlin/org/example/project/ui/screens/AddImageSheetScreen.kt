import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import org.example.project.data.uploadImage.rememberCameraManager
import org.example.project.data.uploadImage.rememberGalleryManager
import org.example.project.ui.viewModels.UploadImageViewModel

@Composable
fun AddImageSheetScreenWrapper(
    viewModel: UploadImageViewModel,
    onCloseClick: () -> Unit
) {
    val image by viewModel.image.collectAsState()

    val cameraManager = rememberCameraManager { result -> viewModel.setImage(result) }
    val galleryManager = rememberGalleryManager { result -> viewModel.setImage(result) }

    AddImageSheetScreen(
        onGalleryClick = { galleryManager.launch() },
        onCameraClick = { cameraManager.launch() },
        onSendClick = { /* send image */ },
        selectedImagePath = image?.localPath,
        onCloseClick = onCloseClick
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddImageSheetScreen(
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit,
    onSendClick: () -> Unit,
    selectedImagePath: String?,
    onCloseClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .graphicsLayer { alpha = 0.95f }
            .background(
                color = Color.White.copy(alpha = 0.15f),
            )
            .blur(0.3.dp)
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Add Image of Plant",
                style = MaterialTheme.typography.titleLarge,
                //color = Color.White.copy(alpha = 0.90f)
            )
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    onClick = onGalleryClick,
                    //colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.18f))
                ) {
                    Icon(Icons.Default.Photo, "Gallery")
                    Spacer(Modifier.width(8.dp))
                    Text("Gallery")
                }
                Button(
                    onClick = onCameraClick,
                   // colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.18f))
                ) {
                    Icon(Icons.Default.CameraAlt, "Camera")
                    Spacer(Modifier.width(8.dp))
                    Text("Camera")
                }
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onSendClick,
                enabled = selectedImagePath != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedImagePath != null)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Send Image")
            }
            Spacer(Modifier.height(16.dp))
            TextButton(onClick = onCloseClick) {
                Text("Cancel", color = Color.White)
            }
        }
    }
}
