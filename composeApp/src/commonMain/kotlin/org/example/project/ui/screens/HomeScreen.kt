package org.example.project.ui.screens

import AddImageSheetScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.data.model.Suggestions
import org.example.project.ui.components.PlantCard
import org.example.project.ui.viewModels.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.MedicalInformation
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import org.example.project.ui.viewModels.HealthViewModel

// Enum to represent the UI state of the Home screen

enum class HomeUiState {
    EMPTY,
    LOADING,
    DATA,
    ERROR
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onDetailsClick: (Suggestions) -> Unit,
    onNavigateToHealth: () -> Unit
) {
    val isShowingAddSheet by viewModel.isShowingAddSheet.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val plantInfo by viewModel.plantInfo.collectAsState()

    val suggestions = plantInfo?.result?.classification?.suggestions.orEmpty()
    val healthViewModel: HealthViewModel = koinViewModel()
    val healthInfo by healthViewModel.healthInfo.collectAsState()
    val healthSuggestions = healthInfo?.result?.disease?.suggestions.orEmpty()
    val hasHealthData = healthSuggestions.isNotEmpty()
    val errorMessage by healthViewModel.errorMessage.collectAsState()
    val uiState = when {
        isLoading -> HomeUiState.LOADING
        plantInfo != null -> HomeUiState.DATA
        errorMessage != null -> HomeUiState.ERROR
        else -> HomeUiState.EMPTY
    }
    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                if (hasHealthData) {
                    FloatingActionButton(
                        onClick = onNavigateToHealth,
                        containerColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 24.dp, bottom = 8.dp)
                    ) {
                        Icon(Icons.Default.MedicalServices, contentDescription = "Go to Health")
                    }
                }

                FloatingActionButton(
                    onClick = viewModel::enableAddSheet,
                    containerColor = Color(0xE8651313),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Icon(Icons.Default.Camera, contentDescription = "Upload Image")
                }
            }
        }
    )

    { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            // Switch between different UI states (Empty, Loading, Data)
            when (uiState) {
                HomeUiState.EMPTY -> {
                    val infiniteTransition = rememberInfiniteTransition()
                    val rotation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(2000, easing = LinearEasing)
                        )
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Scan Prompt",
                            modifier = Modifier
                                .size(100.dp)
                                .rotate(rotation),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Please scan a plant to get started.",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                // Show loading spinner with a loading message
                HomeUiState.LOADING -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Loading plant data...",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                // Show list of plant suggestions
                HomeUiState.DATA -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        items(
                            suggestions.sortedByDescending { it.probability }
                        ) { suggestion ->
                            PlantCard(
                                plant = suggestion,
                                onClick = onDetailsClick  // Trigger details click callback
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
                HomeUiState.ERROR -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = errorMessage ?: "Oops!! something went wrong !!",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            viewModel.clear()
                        }) {
                            Text("Retry")
                        }
                    }
                }

            }
            // Show the Add Image bottom sheet if enabled
            if (isShowingAddSheet) {
                ModalBottomSheet(
                    onDismissRequest = viewModel::disableAddSheet,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.75f)
                ) {
                    AddImageSheetScreen(
                        healthViewModel = healthViewModel,
                        onHealthRequestSent = {
                            onNavigateToHealth() // Navigate to health screen when requested
                        },
                        onCloseClick = viewModel::disableAddSheet
                    )
                }
            }
        }
    }
}
