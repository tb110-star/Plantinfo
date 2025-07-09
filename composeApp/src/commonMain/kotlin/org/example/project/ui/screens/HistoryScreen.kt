package org.example.project.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.example.project.ui.components.HealthHistoryCard
import org.example.project.ui.components.HistoryCategorySelector
import org.example.project.ui.components.PlantHistoryCard
import org.example.project.ui.components.SwipeToDeleteCard
import org.example.project.ui.components.TextScreenStateCard
import org.example.project.ui.viewModels.HistoryItem
import org.example.project.ui.viewModels.HistoryViewModel
import org.koin.compose.viewmodel.koinViewModel
enum class HistoryUiState {
    IDLE,
    LOADING,
    EMPTY,
    DATA,
    ERROR
}
@Composable
fun HistoryScreen(viewModel: HistoryViewModel = koinViewModel()) {
    val category by viewModel.category.collectAsState()
    val plants by viewModel.plantHistory.collectAsState()
    val health by viewModel.healthHistory.collectAsState()

    // Determine UI state based on data availability
    val allItems = when (category) {
        "Plant" -> plants.map { HistoryItem.PlantItem(it) }
        "Health" -> health.map { HistoryItem.HealthItem(it) }
        else -> plants.map { HistoryItem.PlantItem(it) } + health.map { HistoryItem.HealthItem(it) }
    }
    val uiState = when {
        plants.isEmpty() && health.isEmpty() -> HistoryUiState.EMPTY
        else -> HistoryUiState.DATA
    }
    Column {
       /* Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            listOf("All", "Plant", "Health").forEach {
                Button(onClick = { viewModel.setCategory(it) }) {
                    Text(it)
                }
            }
        }

        */
        HistoryCategorySelector(
            selected = category,
            onCategorySelected = { viewModel.setCategory(it) }
        )
        when (uiState) {
            HistoryUiState.LOADING -> {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            HistoryUiState.EMPTY -> {
                TextScreenStateCard(
                    message = "Storage is empty.\nPlease enter and save your first record!"
                )
            }
            HistoryUiState.DATA -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(allItems) { item ->
                        when (item) {
                            is HistoryItem.PlantItem -> {
                                SwipeToDeleteCard(
                                    onDeleteConfirmed = {
                                        viewModel.deletePlant(item.plant.id)
                                    }
                                ) {
                                    PlantHistoryCard(
                                        plant = item.plant,
                                        onDelete = {}                                    )
                                }
                            }
                            is HistoryItem.HealthItem -> {
                                SwipeToDeleteCard(
                                    onDeleteConfirmed = {
                                        viewModel.deleteHealth(item.health.id)
                                    }
                                ) {
                                    HealthHistoryCard(
                                        health = item.health,
                                        onDelete = { }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            HistoryUiState.ERROR -> {
                TextScreenStateCard(
                    message = "An error occurred. Please try again!"
                )
            }

            else -> {}
        }
    }
}
