package org.example.project.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.project.ui.components.HealthHistoryCard
import org.example.project.ui.components.HistoryCategorySelector
import org.example.project.ui.components.PlantHistoryCard
import org.example.project.ui.components.SwipeToDeleteCard
import org.example.project.ui.components.TextScreenStateCard
import org.example.project.ui.viewModels.HistoryItem
import org.example.project.ui.viewModels.HistoryViewModel
import org.koin.compose.viewmodel.koinViewModel
enum class HistoryUiState {

    LOADING,
    EMPTY,
    DATA,
    ERROR
}
@Composable
fun HistoryScreen(viewModel: HistoryViewModel = koinViewModel()) {
    val category by viewModel.category.collectAsState()
    val plants by viewModel.plantHistory.collectAsState(emptyList())
    val health by viewModel.healthHistory.collectAsState(emptyList())

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
                                    )
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


        }
    }
}
