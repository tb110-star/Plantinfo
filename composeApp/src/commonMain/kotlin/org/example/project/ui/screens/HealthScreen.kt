package org.example.project.ui.screens
/*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.error
import kotlinproject.composeapp.generated.resources.placeholder
import org.example.project.ui.components.HealthSummaryCard
import org.example.project.ui.viewModels.HealthInfoViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HealthScreen(
) {

    val viewModel: HealthInfoViewModel = koinViewModel()
    val healthInfo = viewModel.healthInfo.collectAsState()
    val suggestions = healthInfo.value?.result?.disease?.suggestions ?: emptyList()
    var expandedId by remember { mutableStateOf<String?>(null) }
    val allImages = suggestions.flatMap { it.similarImages }
    val enlargedImage = remember { mutableStateOf<String?>(null) }
    val placeholderPainter = painterResource(Res.drawable.placeholder)
    val errorPainter = painterResource(Res.drawable.error)

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
    Column(modifier = Modifier.padding(16.dp)) {
        if (allImages.isNotEmpty()) {

            Text(
                text = "🖼️ Similar Images",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyRow(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(allImages) { image ->
                    AsyncImage(

                       // model = image.urlSmall,
                         model = "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png",

                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        placeholder = placeholderPainter,
                        error = errorPainter,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .clickable {
                                enlargedImage.value = image.url
                            }

                    )
                }
            }

            println("Similar Images URLs:")
            allImages.forEach {
                println(" - small: ${it.urlSmall}")
                println(" - large: ${it.url}")
            }

            enlargedImage.value?.let { imageUrl ->
                Dialog(onDismissRequest = { enlargedImage.value = null }) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        placeholder = placeholderPainter,
                        error = errorPainter,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
        } else {
            Text(
                text = "No similar images available.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
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
}
*/
