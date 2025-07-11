package org.example.project.ui.components
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.error
import kotlinproject.composeapp.generated.resources.placeholder

import org.example.project.data.model.Suggestions
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlantCard(
    plant: Suggestions,
    onClick: (Suggestions) -> Unit
) {
    val placeholderPainter = painterResource(Res.drawable.placeholder)
    val errorPainter = painterResource(Res.drawable.error)
    LaunchedEffect(Unit) {
        println("Image URL: ${plant.details.image?.value}")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(
                onClick = {onClick(plant)}
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color.Yellow.copy(alpha = 0.5f),
                    tonalElevation = 2.dp,
                    modifier = Modifier
                        .size(width = 48.dp, height = 28.dp)
                ) {
                    Text(
                        text = "${(plant.probability * 100).toInt()}%",
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = plant.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                        shape = CircleShape
                    )
            ) {
                AsyncImage(
                    model = plant.details.image?.value,
                    contentDescription = "Plant Image",
                    contentScale = ContentScale.Crop,
                    placeholder = placeholderPainter,
                    error = errorPainter,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}