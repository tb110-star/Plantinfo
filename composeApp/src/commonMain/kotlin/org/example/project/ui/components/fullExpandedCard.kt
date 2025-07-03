package org.example.project.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.error
import kotlinproject.composeapp.generated.resources.placeholder
import org.example.project.data.model.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun SuggestionCard(
    onConfirm: (DiseaseSuggestion) -> Unit,
    suggestion: DiseaseSuggestion,
    isExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    backgroundModifier: Modifier,
    themeColor: Color,
    modifier: Modifier = Modifier

) {

    Card(
        onClick = { onExpandChange(!isExpanded) },
        modifier = modifier
            .padding(vertical = 4.dp)
            .then(backgroundModifier),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                text = suggestion.name ?: "Unknown disease",
                style = MaterialTheme.typography.titleMedium,
                color = themeColor,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier.height(25.dp))
            Spacer(modifier = Modifier.weight(2f))

            Text(
                text = "Probability: ${suggestion.probability}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier.height(10.dp))



            if (isExpanded) {
                suggestion.details?.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                Spacer(Modifier.height(8.dp))

                suggestion.details?.classification?.let {
                    Text(
                        text = "📝 Classification: ${it.joinToString()}",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                suggestion.details?.commonName?.let {
                    Text(
                        text = "🌱 Common Names: ${it.joinToString()}",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                Spacer(Modifier.height(8.dp))

                suggestion.details?.treatment?.let { treatment ->
                    treatment.chemical?.let {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = "⚗️ Chemical: ${it.joinToString()}",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    treatment.biological?.let {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = "🦠 Biological: ${it.joinToString()}",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    treatment.prevention?.let {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = "🛡️ Prevention: ${it.joinToString()}",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                suggestion.similarImages.orEmpty().takeIf { it.isNotEmpty() }?.let { images ->

                    Text(
                        text = "🖼️ Similar Images",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    SimilarImagesRow(
                        images = images,
                         getImageUrlSmall = { it.urlSmall },
                      //  getImageUrlSmall = { "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png" },
                      //  getImageUrlLarge =  { "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png" }
                         getImageUrlLarge = { it.url }
                    )
                    Button(
                        onClick = {
                            val healthEntity =
                                onConfirm(suggestion)
                            onExpandChange(false)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Confirm & Save")
                    }
                }
            }

        }
    }
}

