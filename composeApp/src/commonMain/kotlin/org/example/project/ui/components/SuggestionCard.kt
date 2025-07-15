package org.example.project.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
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
                text = "Confidence: ${suggestion.probability}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFF5E65E)
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
                HorizontalDivider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                Spacer(Modifier.height(8.dp))
                // Show classification
                suggestion.details?.classification
                    .takeIf { !it.isNullOrEmpty() }
                    ?.let { classificationList ->
                        Text(
                            text = "📝 Classification: ${classificationList.joinToString()}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                Spacer(modifier.height(10.dp))

                HorizontalDivider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))

                // Show common names
                suggestion.details?.commonName
                    .takeIf { !it.isNullOrEmpty() }
                    ?.let { commonNames ->
                        Text(
                            text = "🌱 Common Names: ${commonNames.joinToString()}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                Spacer(Modifier.height(8.dp))
                // Treatments (chemical, biological, prevention)
                suggestion.details?.treatment?.let { treatment ->
                    treatment.chemical
                       ?.takeIf { it.isNotEmpty() }
                        ?.let {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = "⚗️ Chemical treatment:\n\n${it.joinToString()}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    treatment.biological
                        ?.takeIf { it.isNotEmpty() }
                        ?.let {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = "🦠 Biological treatment:\n\n${it.joinToString()}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    treatment.prevention
                        ?.takeIf { it.isNotEmpty() }
                        ?.let {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = "🛡️ Prevention treatment:\n\n${it.joinToString()}",
                                style = MaterialTheme.typography.bodyMedium,
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

