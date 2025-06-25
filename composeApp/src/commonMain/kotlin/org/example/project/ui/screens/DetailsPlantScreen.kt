package org.example.project.ui.screens
/*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun DetailsPlantScreen(
    id: String,
){
    LaunchedEffect(Unit) {
        println("Plant Details for ID: $id")
    }
    Column {
        LaunchedEffect(Unit) {
            println("Plant Details for ID: $id")
        }
        Text(
            text = "Details for plant ID: $id",
            modifier = Modifier.padding()
        )
    }
}
*/
// commonMain/kotlin/org/example/project/ui/screens/DetailsPlantScreen.kt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.example.project.data.model.Suggestions
import org.example.project.ui.viewModels.PlantInfoViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
/*
@Composable
fun DetailsPlantScreen(
    suggestion:Suggestions,
    onBack: () -> Unit
) {


    Scaffold {
        Box(Modifier.fillMaxSize()) {
            when {
                suggestion == null -> {
                    Text("Plant not found", Modifier.align(Alignment.Center))
                }

                else -> {
                    val d = suggestion!!.details
                    Column(
                        Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            model = d.image.value,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(Modifier.height(16.dp))
                        Text("Description", style = MaterialTheme.typography.titleMedium)
                        Text(d.description.value)
                        Spacer(Modifier.height(12.dp))
                        val wMin = d.watering?.min ?: "-"
                        val wMax = d.watering?.max ?: "-"
                        Text("Watering: $wMin–$wMax times/week")
                        Text("Light: ${d.bestLightCondition}")
                        Text("Soil: ${d.bestSoilType}")
                        Text("Uses: ${d.commonUses}")
                    }
                }
            }
        }
    }
}


 */
@Composable
fun DetailsPlantScreen(
    suggestion: Suggestions,
    onBack: () -> Unit
) {
    val d = suggestion.details
    val wMin = d.watering?.min ?: "-"
    val wMax = d.watering?.max ?: "-"

    Scaffold() { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = d.image.value,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(Modifier.height(16.dp))

                    Text("Description", style = MaterialTheme.typography.titleMedium)
                    Text(d.description.value, style = MaterialTheme.typography.bodyMedium)

                    Spacer(Modifier.height(12.dp))
                    Text("Watering", style = MaterialTheme.typography.titleMedium)
                    Text("➤ $wMin–$wMax times/week", style = MaterialTheme.typography.bodyMedium)

                    Spacer(Modifier.height(12.dp))
                    Text("Light", style = MaterialTheme.typography.titleMedium)
                    Text("➤ ${d.bestLightCondition}", style = MaterialTheme.typography.bodyMedium)

                    Spacer(Modifier.height(12.dp))
                    Text("Soil", style = MaterialTheme.typography.titleMedium)
                    Text("➤ ${d.bestSoilType}", style = MaterialTheme.typography.bodyMedium)

                    Spacer(Modifier.height(12.dp))
                    Text("Uses", style = MaterialTheme.typography.titleMedium)
                    Text("➤ ${d.commonUses}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
