import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.data.imagePickerPermission.shared.createImageManager
import org.example.project.ui.viewModels.HealthViewModel
import org.example.project.ui.viewModels.HomeViewModel
import org.example.project.ui.viewModels.UploadImageViewModel
import org.koin.compose.viewmodel.koinViewModel
import shared.PermissionCallback
import shared.PermissionStatus
import shared.PermissionType
import shared.createPermissionsManager

@Composable
fun AddImageSheetScreen(
    onHealthRequestSent: () -> Unit,
    onCloseClick: () -> Unit
) {
    val uploadImageViewModel: UploadImageViewModel = koinViewModel()
    val homeViewModel: HomeViewModel = koinViewModel()
    val healthViewModel:HealthViewModel = koinViewModel()
    val coroutineScope = rememberCoroutineScope()
    val image by uploadImageViewModel.image.collectAsState()
    val imageBitmapState = remember { mutableStateOf<ImageBitmap?>(null) }
    val base64 by uploadImageViewModel.base64.collectAsState()

    val launchCamera = remember { mutableStateOf(false) }
    val launchGallery = remember { mutableStateOf(false) }
    val launchSetting = remember { mutableStateOf(false) }
    val permissionRationalDialog = remember { mutableStateOf(false) }

    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> launchCamera.value = true
                        PermissionType.GALLERY -> launchGallery.value = true
                    }
                }
                else -> permissionRationalDialog.value = true
            }
        }
    })

    val imageManager = createImageManager { byteArray ->
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                byteArray?.decodeToImageBitmap()
            }
            uploadImageViewModel.setImage(byteArray)
            imageBitmapState.value = bitmap
        }
    }


    // Permission Launch
    if (launchGallery.value) {
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
            imageManager.launchGallery()
        } else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
        launchGallery.value = false
    }

    if (launchCamera.value) {
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
            imageManager.launchCamera()
        } else {
            permissionsManager.askPermission(PermissionType.CAMERA)
        }
        launchCamera.value = false
    }

    if (launchSetting.value) {
        permissionsManager.launchSettings()
        launchSetting.value = false
    }

    if (permissionRationalDialog.value) {
        AlertMessageDialog(
            title = "Permission Required",
            message = "to use gallery or camera,needs permission",
            positiveButtonText = "Settings",
            negativeButtonText = "Cancel",
            onPositiveClick = {
                permissionRationalDialog.value = false
                launchSetting.value = true
            },
            onNegativeClick = { permissionRationalDialog.value = false }
        )
    }

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
            .graphicsLayer { alpha = 0.95f }
            .background(Color.White.copy(alpha = 0.05f))
            .blur(0.3.dp)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Add Image of Plant", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(24.dp))

                val buttonModifier = Modifier.width(160.dp)

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { launchGallery.value = true },
                        modifier = buttonModifier
                    ) {
                        Icon(Icons.Default.Photo, contentDescription = "Gallery")
                        Spacer(Modifier.width(8.dp))
                        Text("Gallery")
                    }

                    Button(
                        onClick = { launchCamera.value = true },
                        modifier = buttonModifier
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Camera")
                        Spacer(Modifier.width(8.dp))
                        Text("Camera")
                    }
                }

                Spacer(Modifier.height(32.dp))

                if (imageBitmapState.value != null) {
                    Image(
                        bitmap = imageBitmapState.value!!,
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(180.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                    Spacer(Modifier.height(32.dp))
                }

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            base64?.let { base64Str ->
                                println("Sending request with base64: ${base64?.take(30)}")
                                homeViewModel.clear()

                                val request = uploadImageViewModel.createRequest(base64Str)
                                homeViewModel.loadPlantInfo(request)
                                onCloseClick()
                                uploadImageViewModel.clear()
                                imageBitmapState.value = null
                            }
                        },
                        enabled = image != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (image != null && base64 != null)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                        ),
                        modifier = buttonModifier
                    ) {
                        Text("Get Plant Info")
                    }

                    Button(
                        onClick = {
                            base64?.let { base64Str ->
                                println("Sending request with base64: ${base64?.take(30)}")
                                healthViewModel.clear()
                                val request = uploadImageViewModel.createRequest(base64Str)
                                healthViewModel.loadHealthInfo(request)
                                onCloseClick()
                                onHealthRequestSent()
                                uploadImageViewModel.clear()
                                imageBitmapState.value = null
                            }
                        },
                        enabled = image != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (image != null && base64 != null)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                        ),
                        modifier = buttonModifier
                    ) {
                        Text("Get Health Info")
                    }
                }
            }

            TextButton(onClick = onCloseClick) {
                Text("Cancel")
            }
        }
    }

}

