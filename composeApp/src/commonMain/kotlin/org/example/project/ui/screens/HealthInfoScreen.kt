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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import org.example.project.ui.components.SimilarImagesRow


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun HealthInfoScreen(
) {

    val viewModel: HealthInfoViewModel = koinViewModel()
    val healthInfo = viewModel.healthInfo.collectAsState()
    val suggestions = healthInfo.value?.result?.disease?.suggestions ?: emptyList()
    var expandedId by remember { mutableStateOf<String?>(null) }
    val allImages = suggestions.flatMap { it.similarImages }

    LaunchedEffect(Unit) {
        println(" All health suggestions:")
        viewModel.loadHealthInfo()

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
                Column {
                    Text(
                        text = "Similar Images",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
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
                    glassyBackground
                )
            }

/////

            items(suggestions.chunked(2)) { rowItems ->
                val expandedInRow = rowItems.firstOrNull { it.id == expandedId }

                if (expandedInRow != null) {
                    SuggestionCard(
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
                }
            }

            /////
            healthInfo.value?.result?.disease?.question?.let { ques ->
                item {
                    QuestionCard(
                        question = ques,
                        backgroundModifier = Modifier
                    )
                }
            }
        }
    }
}
