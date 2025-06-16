package org.example.project.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.ui.components.PickImageButton
import org.example.project.ui.viewModels.PlantInfoViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun PlantInfoScreen(
    viewModel: PlantInfoViewModel = koinViewModel<PlantInfoViewModel>()
){
val plantInfo = viewModel.plantInfo.collectAsState()
    val suggestions = plantInfo.value?.result?.classification?.suggestions ?: emptyList()

    Column {
        Button(
            onClick = { viewModel.loadPlantInfo() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Load Plant Info")
        }

        if (plantInfo != null) {
            LazyColumn {
                items(suggestions) { suggestion ->
                    Text(text = "${suggestion.name} - Confidence: ${(suggestion.probability * 100).toInt()}%")

                }
            }


        } else {
            Text(
                text = "No plant info loaded yet.",
                modifier = Modifier.padding(8.dp)
            )
        }
        PickImageButton(
            onPickImage = { }
        )
    }
}