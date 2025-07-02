package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.example.project.data.local.roomModel.toPlantHistory
import org.example.project.data.model.Suggestions
import org.example.project.ui.components.ExpandableInfoCard
import org.example.project.ui.components.SimilarImagesRow
import org.example.project.ui.viewModels.HealthInfoViewModel
import org.example.project.ui.viewModels.PlantInfoViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailsPlantScreen(
    suggestion: Suggestions,
    onBack: () -> Unit
) {
    val vM: PlantInfoViewModel = koinViewModel()
    val viewModel: HealthInfoViewModel = koinViewModel()
    val healthInfo = viewModel.healthInfo.collectAsState()
    val suggestions = healthInfo.value?.result?.disease?.suggestions ?: emptyList()

    val d = suggestion.details
    val wMin = d.watering?.min ?: "-"
    val wMax = d.watering?.max ?: "-"

    Scaffold { innerPadding ->
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
                val wateringInfo = suggestion.details.watering
                val bestWatering = suggestion.details.bestWatering.orEmpty()

                val shortWatering = buildString {
                    if (wateringInfo != null) {
                        append("Min:${wateringInfo.min} , Max: ${wateringInfo.max}\n")
                    }
                }.ifEmpty { "No watering data available." }

                val restWatering = if (bestWatering.isNotBlank()) {
                    "Best watering:\n\n$bestWatering"
                } else {
                    "No Best watering data available."
                }
                ExpandableInfoCard(
                    title = "Watering",
                    icon = Icons.Default.WaterDrop,
                    iconColor = Color(0xFF42A5F5),
                    shortDescription = shortWatering,
                    remainingDescription = restWatering,
                    backgroundColor = Color(0xFFB3E5FC).copy(alpha = 0.7f)
                )


                Spacer(Modifier.height(12.dp))

// Light
                val lightText = suggestion.details.bestLightCondition?.takeIf { it.isNotBlank() } ?: "No light data available."
                val (shortLight, restLight) = vM.splitTextSmart(lightText)

                ExpandableInfoCard(
                    title = "Light",
                    icon = Icons.Default.WbSunny,
                    iconColor = Color(0xFFFFC107),
                    shortDescription = shortLight,
                    remainingDescription = restLight,
                    backgroundColor = Color(0xFFE0E3BC).copy(alpha = 0.7f)
                )

                Spacer(Modifier.height(12.dp))

// Soil
                val soilText = suggestion.details.bestSoilType?.takeIf { it.isNotBlank() } ?: "No soil data available."
                val (shortSoil, restSoil) = vM.splitTextSmart(soilText)

                ExpandableInfoCard(
                    title = "Soil",
                    icon = Icons.Default.Landscape,
                    iconColor = Color(0xFF795548),
                    shortDescription = shortSoil,
                    remainingDescription = restSoil,
                    backgroundColor = Color(0xFFD7CCC8).copy(alpha = 0.7f)
                )

                Spacer(Modifier.height(12.dp))

// Uses
                val usesText = suggestion.details.commonUses?.takeIf { it.isNotBlank() } ?: "No usage data available."
                val (shortUses, restUses) = vM.splitTextSmart(usesText)

                ExpandableInfoCard(
                    title = "Uses",
                    icon = Icons.Default.Spa,
                    iconColor = Color(0xFF3EB6AB),
                    shortDescription = shortUses,
                    remainingDescription = restUses,
                    backgroundColor = Color(0xFFCCAAAA).copy(alpha = 0.7f)
                )


            }
        }
        LaunchedEffect(Unit) {
            val plantEntity = suggestion.toPlantHistory()
            vM.saveToHistory(plantEntity)
        }

    }
}

