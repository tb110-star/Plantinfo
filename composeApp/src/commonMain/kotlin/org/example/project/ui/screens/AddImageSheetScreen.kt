import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.ui.viewModels.HealthInfoViewModel
import org.example.project.ui.viewModels.PlantInfoViewModel

import org.example.project.ui.viewModels.UploadImageViewModel
import org.koin.compose.viewmodel.koinViewModel
import shared.PermissionCallback
import shared.PermissionStatus
import shared.PermissionType
import shared.createPermissionsManager
import shared.rememberCameraManager
import shared.rememberGalleryManager
@Composable
fun AddImageSheetScreen(

    onCloseClick: () -> Unit
) {
    val uploadImageViewModel: UploadImageViewModel = koinViewModel()
    val plantInfoViewModel: PlantInfoViewModel = koinViewModel()
    val healthInfoViewModel:HealthInfoViewModel = koinViewModel()
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

    val cameraManager = rememberCameraManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) { it?.toImageBitmap() }
            uploadImageViewModel.setImage(it)
            imageBitmapState.value = bitmap
        }
    }

    val galleryManager = rememberGalleryManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) { it?.toImageBitmap() }
            uploadImageViewModel.setImage(it)
            imageBitmapState.value = bitmap
        }
    }

    // Permission Launch
    if (launchGallery.value) {
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
            galleryManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
        launchGallery.value = false
    }

    if (launchCamera.value) {
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
            cameraManager.launch()
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

    //
    Box(
        Modifier
            .fillMaxWidth()
            .graphicsLayer { alpha = 0.95f }
            .background(Color.White.copy(alpha = 0.15f))
            .blur(0.3.dp)
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Image of Plant", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { launchGallery.value = true }) {
                    Icon(Icons.Default.Photo, "Gallery")
                    Spacer(Modifier.width(8.dp))
                    Text("Gallery")
                }

                Button(onClick = { launchCamera.value = true }) {
                    Icon(Icons.Default.CameraAlt, "Camera")
                    Spacer(Modifier.width(8.dp))
                    Text("Camera")
                }
            }

            Spacer(Modifier.height(24.dp))
            if (imageBitmapState.value != null) {
                Spacer(Modifier.height(24.dp))
                Image(
                    bitmap = imageBitmapState.value!!,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            }
            Spacer(Modifier.height(16.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
            Button(
                onClick = {
                    base64?.let { base64Str ->
                        val request = uploadImageViewModel.createRequest(base64Str)
                        plantInfoViewModel.loadPlantInfo(request)
                        onCloseClick()
                    }
                },
                enabled = image != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (image != null)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Get Plant Info")
            }
                Button(
                    onClick =  {
                        base64?.let { base64Str ->
                        val request = uploadImageViewModel.createRequest(base64Str)
                        healthInfoViewModel.loadHealthInfo(request)
                        onCloseClick()
                            //
                            //  health screen navigation
                          //
                           // navigateToHealthScreen()
                        }
                    },
                    enabled = image != null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (image != null)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Get Health Info")
                }
        }
            Spacer(Modifier.height(16.dp))

            TextButton(onClick = onCloseClick) {
                Text("Cancel")
            }
        }
    }
}