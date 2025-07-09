package org.example.project.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import org.example.project.ui.components.HealthSummaryCard
import org.example.project.ui.components.QuestionCard
import org.example.project.ui.components.SuggestionCard
import org.example.project.ui.viewModels.HealthViewModel
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import org.example.project.ui.components.TextScreenStateCard


enum class HealthUiState {
    LOADING,
    DATA,
    ERROR
}
@Composable
fun HealthScreen(
    healthViewModel: HealthViewModel
) {
    val healthInfo by healthViewModel.healthInfo.collectAsState()
    val isLoading by healthViewModel.isLoading.collectAsState()
    val errorMessage by healthViewModel.errorMessage.collectAsState()
    val suggestions = healthInfo?.result?.disease?.suggestions.orEmpty()
    var expandedId by remember { mutableStateOf<String?>(null) }// Holds the ID of the currently expanded SuggestionCard, or null if none is expanded
    val uiState = when {
        isLoading -> HealthUiState.LOADING
        errorMessage != null || healthInfo == null -> HealthUiState.ERROR
        else -> HealthUiState.DATA
    }

    val glassyBackground = Modifier
        .background(
            Brush.verticalGradient(
                listOf(
                    MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                )
            ),
            RoundedCornerShape(16.dp)
        )
        .blur(0.1.dp)

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                HealthUiState.LOADING -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        TextScreenStateCard(
                            message = "Loading health data..."
                        )
                    }
                }

                HealthUiState.ERROR -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextScreenStateCard(
                            message = "Oops!! something went wrong !!\nRetry"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                    }
                }

                HealthUiState.DATA -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        // Summary card
                        item {
                            healthInfo?.let {
                                HealthSummaryCard(healthInfo = it)
                                Spacer(Modifier.height(16.dp))
                            }
                        }

                        // Suggestions
                        if (expandedId == null) {
                            val grouped = suggestions.chunked(2)
                            items(grouped.size) { rowIndex ->
                                val rowItems = grouped[rowIndex]
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    rowItems.forEach { suggestion ->
                                        SuggestionCard(
                                            onConfirm = { confirmed ->
                                                healthViewModel.saveConfirmedSuggestion(confirmed)
                                            },
                                            suggestion = suggestion,
                                            isExpanded = false,
                                            onExpandChange = { isExpanded ->
                                                expandedId = if (isExpanded) suggestion.id else null
                                            },
                                            backgroundModifier = glassyBackground,
                                            themeColor = MaterialTheme.colorScheme.onBackground,
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(120.dp)
                                        )
                                    }
                                    if (rowItems.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                                Spacer(Modifier.height(16.dp))
                            }
                        } else {    // One card is expanded, find it by its ID
                            val expandedSuggestion = suggestions.firstOrNull { it.id == expandedId }
                            expandedSuggestion?.let { suggestion ->
                                item {
                                    SuggestionCard(
                                        onConfirm = { confirmed ->
                                            healthViewModel.saveConfirmedSuggestion(confirmed)
                                        },
                                        suggestion = suggestion,
                                        isExpanded = true,// This is the expanded card
                                        onExpandChange = { isExpanded ->// If collapsed, clear expandedId
                                            expandedId = if (isExpanded) suggestion.id else null
                                        },
                                        backgroundModifier = glassyBackground,
                                        themeColor = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                    )
                                    Spacer(Modifier.height(16.dp))
                                }
                            }
                        }

                        // Question
                        healthInfo?.result?.disease?.question?.let { question ->
                            item {
                                QuestionCard(
                                    question = question,
                                    selectedSuggestion = healthViewModel.selectedSuggestion.collectAsState().value,
                                    onAnswerSelected = { isYes ->
                                        healthViewModel.onQuestionAnswered(isYes, question)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
