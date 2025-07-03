package org.example.project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.websocket.Frame
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.error
import kotlinproject.composeapp.generated.resources.placeholder
import org.example.project.ui.components.HealthHistoryCard
import org.example.project.ui.components.PlantHistoryCard
import org.example.project.ui.viewModels.HistoryItem
import org.example.project.ui.viewModels.HistoryViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HistoryScreen(viewModel: HistoryViewModel = koinViewModel()) {
    val category by viewModel.category.collectAsState()
    val plants by viewModel.plantHistory.collectAsState()
    val health by viewModel.healthHistory.collectAsState()
    val allItems = when(category) {
        "Plant" -> plants.map { HistoryItem.PlantItem(it) }
        "Health" -> health.map { HistoryItem.HealthItem(it) }
        else -> plants.map { HistoryItem.PlantItem(it) } + health.map { HistoryItem.HealthItem(it) }
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            listOf("All", "Plant", "Health").forEach {
                Button(onClick = { viewModel.setCategory(it) }) {
                    Text(it)
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(allItems) { item ->
                when (item) {
                    is HistoryItem.PlantItem -> {
                        PlantHistoryCard(
                            plant = item.plant,
                            onDelete = {
                              //  viewModel.deletePlant(item.plant.id)
                            }
                        )
                    }
                    is HistoryItem.HealthItem -> {
                        HealthHistoryCard(
                            health = item.health,
                            onDelete = {
                                //viewModel.deleteHealth(item.health.id)
                            }
                        )
                    }
                }
            }
        }

    }
}
