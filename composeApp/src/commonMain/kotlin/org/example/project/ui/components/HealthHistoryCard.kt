package org.example.project.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.error
import kotlinproject.composeapp.generated.resources.placeholder
import org.example.project.data.local.roomModel.HealthHistoryEntity
import org.jetbrains.compose.resources.painterResource

@Composable
fun HealthHistoryCard(
    health: HealthHistoryEntity,
) {
    var expanded by remember { mutableStateOf(false) }
    val imageUrl = health.imageUploadedUrl
    val placeholderPainter = painterResource(Res.drawable.placeholder)
    val errorPainter = painterResource(Res.drawable.error)
    val confidencePercent = ((health.probability ?: 0.0) * 100).toInt()


    Card(
        onClick = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = { expanded = !expanded },
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(health.diseaseName ?: "No name", style = MaterialTheme.typography.titleMedium)
                    Text("Health", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)

                    Text("Confidence: $confidencePercent%", style = MaterialTheme.typography.bodySmall, color = Color.Yellow)
                }

                imageUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        placeholder = placeholderPainter,
                        error = errorPainter,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .let { base ->
                                if (expanded) {
                                    base
                                        .size(120.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                } else {
                                    base
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(50))
                                }

                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }

            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Spacer(Modifier.height(8.dp))
                health.description
                    .takeIf { it != "No description available" && it.isNotBlank() }
                    ?.let {
                        Text(
                            text = "📝 Description  :  $it",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.1f))
                                .padding(8.dp)
                        )
                    }
                health.classification
                    ?.takeIf { it != "No classification available" }
                    ?.let {
                        Text(
                            text = "📚 Classification  :  $it",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.05f))
                                .padding(8.dp)
                        )
                    }

                health.commonNames
                    ?.takeIf { it != "No common names available" }
                    ?.let {
                        Text(
                            text = "🪴 Common Names  :  $it",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.05f))
                                .padding(8.dp)
                        )
                    }

                health.chemicalTreatment
                    ?.takeIf { it != "No chemical treatment available" }
                    ?.let {
                        Text(
                            text = "🧪 Chemical Treatment  :  $it",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.05f))
                                .padding(8.dp)
                        )
                    }

                health.biologicalTreatment
                    ?.takeIf { it != "No biological treatment available" }
                    ?.let {
                        Text(
                            text = "🧬 Biological Treatment  :  $it",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.05f))
                                .padding(8.dp)
                        )
                    }

                health.preventionTreatment
                    ?.takeIf { it != "No prevention treatment available" }
                    ?.let {
                        Text(
                            text = "🚫 Prevention  :  $it",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.05f))
                                .padding(8.dp)
                        )
                    }
            }
        }
    }
}
