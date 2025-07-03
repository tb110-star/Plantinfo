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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val imageUrl = health.imageUploadedUrl
    println("Trying to load image: $imageUrl for plant: ${health.diseaseName}")

    val placeholderPainter = painterResource(Res.drawable.placeholder)
    val errorPainter = painterResource(Res.drawable.error)

    Card(
        onClick = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = { expanded = !expanded },
                onLongClick = { onDelete() }
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(health.diseaseName ?: "No name", style = MaterialTheme.typography.titleMedium)
                    Text("Health", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)

                    Text("Probability: ${(health.probability ?: 0.0 * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }

                imageUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        placeholder = placeholderPainter,
                        error = errorPainter,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .height(if (expanded) 80.dp else 50.dp)
                            .fillMaxWidth(0.2f),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "\uD83D\uDCDD Classification:${health.description}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Black.copy(alpha = 0.1f))
                        .padding(8.dp)
                )
                Text(
                    text = "❓ Answered Q: ${health.questionAnswered ?: "N/A"}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Black.copy(alpha = 0.1f))
                        .padding(8.dp)
                )

            }
        }
    }
}
