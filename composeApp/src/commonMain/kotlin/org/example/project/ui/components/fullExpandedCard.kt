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
                suggestion.name,
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

        }
            if (isExpanded) {
                suggestion.details.description?.let {
                    Text(it, style = MaterialTheme.typography.bodyMedium)
                }
                suggestion.details.commonName?.let {
                    Text("Common Names: ${it.joinToString()}", style = MaterialTheme.typography.bodySmall)
                }
                suggestion.details.classification?.let {
                    Text("Classification: ${it.joinToString()}", style = MaterialTheme.typography.bodySmall)
                }
                suggestion.details.treatment?.let { treatment ->
                    treatment.chemical?.let { Text("Chemical: ${it.joinToString()}", style = MaterialTheme.typography.bodySmall) }
                    treatment.biological?.let { Text("Biological: ${it.joinToString()}", style = MaterialTheme.typography.bodySmall) }
                    treatment.prevention?.let { Text("Prevention: ${it.joinToString()}", style = MaterialTheme.typography.bodySmall) }
                }
                Spacer(modifier.height(12.dp))

                suggestion.similarImages.takeIf { it.isNotEmpty() }?.let { images ->
                    Text(
                        text = "Similar Images",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = modifier.padding(vertical = 8.dp)
                    )
                    SimilarImagesRow(
                        images = images,
                        getImageUrlSmall = {it.urlSmall},
                        getImageUrlLarge = {it.url}
                    )
            }
        }
    }
}


