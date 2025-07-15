package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.example.project.data.model.Suggestions
import org.example.project.ui.components.ExpandableInfoCard
import org.example.project.ui.components.SimilarImagesRow
import org.example.project.ui.viewModels.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailsPlantScreen(
    vM: HomeViewModel  = koinViewModel(),
    suggestion: Suggestions,
    onBack: () -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val showSnackbar = remember { mutableStateOf(false) }
    val snackbarMessage = remember { mutableStateOf("") }


    val d = suggestion.details
    LaunchedEffect(showSnackbar.value) {
        if (showSnackbar.value) {
            snackbarHostState.showSnackbar(
                message = snackbarMessage.value,
                duration = SnackbarDuration.Indefinite
            )
            showSnackbar.value = false
        }
    }


    Scaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
        FloatingActionButton(
            onClick = {
              showDialog.value = true
            },
            containerColor = Color(0xE8D29B9B)
            ) {
            Icon(Icons.Default.Save, contentDescription = "Save Plant Info")
        }
    }
    ){ innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {

                AsyncImage(
                    model = d.image?.value,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(Modifier.height(12.dp))

                suggestion.similarImages.takeIf { it.isNotEmpty() }?.let { images ->
                    Text(
                        text = "Similar Images",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    SimilarImagesRow(
                        images = images,
                        getImageUrlSmall = {it.urlSmall},
                        getImageUrlLarge = {it.url}
                    )
                    Spacer(Modifier.height(16.dp))
                }

                val descText = suggestion.details.description?.value ?: "No description available."
                val (shortDesc, restDesc) = vM.splitTextSmart(descText)
                ExpandableInfoCard(
                    title = "Description",
                    icon = Icons.Default.Info,
                    iconColor = Color(0xFF966BE1),
                    shortDescription = shortDesc,
                    remainingDescription = restDesc,
                    backgroundColor = Color(0xFF94BDB9).copy(alpha = 0.7f)
                )


                Spacer(Modifier.height(12.dp))
                // Watering
                suggestion.details.watering?.let { w ->
                    val short = "Min:${w.min} , Max:${w.max}"
                    val rest = suggestion.details.bestWatering.orEmpty()
                    ExpandableInfoCard(
                        title = "Watering",
                        icon = Icons.Default.WaterDrop,
                        iconColor = Color(0xFF42A5F5),
                        shortDescription = short,
                        remainingDescription = rest,
                        backgroundColor = Color(0xFFB3E5FC).copy(alpha = 0.7f)
                    )
                    Spacer(Modifier.height(12.dp))
                }

// Light

                suggestion.details.bestLightCondition
                    .orEmpty()
                    .takeIf(String::isNotBlank)
                    ?.let { text ->
                        val (short, rest) = vM.splitTextSmart(text)
                        ExpandableInfoCard(
                            title = "Light",
                            icon = Icons.Default.WbSunny,
                            iconColor = Color(0xFFFFC107),
                            shortDescription = short,
                            remainingDescription = rest,
                            backgroundColor = Color(0xFFE0E3BC).copy(alpha = 0.7f)
                        )
                        Spacer(Modifier.height(12.dp))
                    }

// Soil
                suggestion.details.bestSoilType
                    .orEmpty()
                    .takeIf(String::isNotBlank)
                    ?.let { text ->
                        val (short, rest) = vM.splitTextSmart(text)
                        ExpandableInfoCard(
                            title = "Soil",
                            icon = Icons.Default.Landscape,
                            iconColor = Color(0xFF795548),
                            shortDescription = short,
                            remainingDescription = rest,
                            backgroundColor = Color(0xFFD7CCC8).copy(alpha = 0.7f)
                        )
                        Spacer(Modifier.height(12.dp))
                    }
// Uses
                suggestion.details.commonUses
                    .orEmpty()
                    .takeIf(String::isNotBlank)
                    ?.let { text ->
                        val (short, rest) = vM.splitTextSmart(text)
                        ExpandableInfoCard(
                            title = "Uses",
                            icon = Icons.Default.Spa,
                            iconColor = Color(0xFF3EB6AB),
                            shortDescription = short,
                            remainingDescription = rest,
                            backgroundColor = Color(0xFFCCAAAA).copy(alpha = 0.7f)
                        )
                        Spacer(Modifier.height(12.dp))
                    }
            }
        }
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Confirm Save") },
            text = { Text("Do you want to save this plant to history?") },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            vM.saveToPlantHistory(
                                suggestion = suggestion,
                                serverImageUrl = vM.serverImageUrl.value,
                            ) { success ->
                                snackbarMessage.value = if (success) "Saved successfully!" else "Error saving!"

                                showSnackbar.value = true
                                showDialog.value = false
                                onBack()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save")
                    }

                    Button(
                        onClick = { showDialog.value = false },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancle")
                    }

                }
            }
        )
    }




}

