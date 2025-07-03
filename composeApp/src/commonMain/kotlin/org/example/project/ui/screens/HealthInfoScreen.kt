package org.example.project.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import org.example.project.ui.components.HealthSummaryCard
import org.example.project.ui.components.QuestionCard
import org.example.project.ui.components.SuggestionCard
import org.example.project.ui.viewModels.HealthInfoViewModel
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import org.example.project.data.local.roomModel.toHealthHistory
import org.example.project.data.local.roomModel.toPlantHistory
import org.example.project.data.model.DiseaseSuggestion
import org.example.project.ui.components.SimilarImagesRow
import org.example.project.ui.viewModels.UploadImageViewModel
import org.koin.compose.getKoin

/*
@Composable
fun HealthInfoScreen(
) {

    val viewModel: HealthInfoViewModel = koinViewModel()
    val healthInfo = viewModel.healthInfo.collectAsState()
    val suggestions = healthInfo.value?.result?.disease?.suggestions ?: emptyList()
    var expandedId by remember { mutableStateOf<String?>(null) }
    val allImages = suggestions.flatMap { it.similarImages ?: emptyList() }
    val selectedSuggestion by viewModel.selectedSuggestion.collectAsState()

    LaunchedEffect(Unit) {
        println("Suggestion count = ${suggestions.size}")
        suggestions.forEach {
            println("Suggestion ID: ${it.id}")
        }
    }
    val glassyBackground = Modifier
        .background(
            Brush.verticalGradient(
                listOf(
                    MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                    // Color.White.copy(alpha = 0.2f),
                    //  Color.Black.copy(alpha = 0.07f)
                )
            ),
            RoundedCornerShape(16.dp)
        )
        .blur(0.1.dp)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        allImages.takeIf { it.isNotEmpty() }?.let { images ->
            item {
                Text("Suggestions count: ${suggestions.size}")

                Column {
                    /*
                    Text(
                        text = "Similar Images",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    */
                    SimilarImagesRow(
                        images = images,
                        getImageUrlSmall = { it.urlSmall },
                        getImageUrlLarge = { it.url }
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }
        }

        healthInfo.value?.let { info ->
            item {
                HealthSummaryCard(
                    healthInfo = info,
                )
                Spacer(Modifier.height(16.dp))

            }
/////
            items(suggestions.chunked(2)) { rowItems ->
                val expandedInRow = rowItems.firstOrNull { it.id == expandedId }

                if (expandedInRow != null) {
                    SuggestionCard(
                        onConfirm = {confirmedSuggestion ->
                            val entity = confirmedSuggestion.toHealthHistory(
                                healthStatus = healthInfo.value?.result?.isHealthy?.binary.toString(),
                                selectedSuggestion = viewModel.selectedSuggestion.value
                            )
                            viewModel.saveToHealthHistory(entity)
                        },
                        suggestion = expandedInRow,
                        isExpanded = true,
                        onExpandChange = { isExpanded ->
                            expandedId = if (isExpanded) expandedInRow.id else null
                        },
                        glassyBackground,
                        themeColor = MaterialTheme.colorScheme.onBackground
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { suggestion ->
                            SuggestionCard(
                                onConfirm = {confirmedSuggestion ->
                                    val entity = confirmedSuggestion.toHealthHistory(
                                        healthStatus = healthInfo.value?.result?.isHealthy?.binary.toString(),
                                        selectedSuggestion = viewModel.selectedSuggestion.value
                                    )
                                    viewModel.saveToHealthHistory(entity)
                                },
                                suggestion = suggestion,
                                isExpanded = false,
                                onExpandChange = { isExpanded ->
                                    expandedId = if (isExpanded) suggestion.id else null
                                },
                                glassyBackground,
                                themeColor = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(120.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }

            healthInfo.value?.result?.disease?.question?.let { ques ->
                item {
                    QuestionCard(
                        question = ques,
                        selectedSuggestion = selectedSuggestion,
                        onAnswerSelected = { isYes ->
                            viewModel.onQuestionAnswered(isYes, ques)
                        }
                    )
                }
            }
        }
    }
}
 */
@Composable
fun HealthInfoScreen() {
    val uploadImageViewModel: UploadImageViewModel = getKoin().get()
    val request by uploadImageViewModel.request.collectAsState()
    val viewModel: HealthInfoViewModel = koinViewModel()
    val healthInfo by viewModel.healthInfo.collectAsState()
    val suggestions = healthInfo?.result?.disease?.suggestions.orEmpty()
    var expandedId by remember { mutableStateOf<String?>(null) }
    val isLoading by viewModel.isLoading.collectAsState()

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

    LaunchedEffect(request) {
        request?.let {
            println("Sending request in HealthInfoScreen: $it")
            viewModel.loadHealthInfo(it)
        }
    }
    LaunchedEffect(healthInfo, isLoading) {
        println("Loading: $isLoading, HealthInfo: $healthInfo")
    }
    LaunchedEffect(Unit) {
        println(" HealthInfoScreen Launched")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {
            if (healthInfo == null && !isLoading) {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("No health info loaded.")
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = {
                            viewModel.refreshUI()
                        }) {
                            Text("Retry Show")
                        }
                    }
                }
            }
            item {
            healthInfo?.let {
                HealthSummaryCard(healthInfo = it)
                Spacer(Modifier.height(16.dp))
            }
        }
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
                            onConfirm = { confirmed->
                                viewModel.saveConfirmedSuggestion(confirmed)
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
        } else {
            val expandedSuggestion = suggestions.firstOrNull { it.id == expandedId }
            expandedSuggestion?.let { suggestion ->
                item {
                    SuggestionCard(
                        onConfirm = { confirmed->
                            viewModel.saveConfirmedSuggestion(confirmed)
                        },
                        suggestion = suggestion,
                        isExpanded = true,
                        onExpandChange = { isExpanded ->
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

        healthInfo?.result?.disease?.question?.let { question ->
            item {
                QuestionCard(
                    question = question,
                    selectedSuggestion = viewModel.selectedSuggestion.collectAsState().value,
                    onAnswerSelected = { isYes ->
                        viewModel.onQuestionAnswered(isYes, question)
                    }
                )
            }
        }
    }
}
    }