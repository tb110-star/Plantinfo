package org.example.project.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.data.model.*

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
        onClick = { onExpandChange(!isExpanded) },  // Toggle expand/collapse on click
        modifier = modifier
            .padding(vertical = 4.dp)
            .then(backgroundModifier),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            // Disease name
            Text(
                text = suggestion.name ?: "Unknown disease",
                style = MaterialTheme.typography.titleMedium,
                color = themeColor,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier.height(25.dp))
            Spacer(modifier = Modifier.weight(2f))
            // Probability of the disease
            Text(
                text = "Probability: ${suggestion.probability}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier.height(10.dp))

            if (isExpanded) {
                // Show description if available
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
                // Show classification
                suggestion.details?.classification?.let {
                    Text(
                        text = "📝 Classification: ${it.joinToString()}",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                // Show common names
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
                // Treatments (chemical, biological, prevention)
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
                // Similar images
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
                    }
                    Button(
                        onClick = {
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

