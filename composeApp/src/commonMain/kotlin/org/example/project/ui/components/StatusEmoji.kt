package org.example.project.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

@Composable

fun StatusEmoji(prob: Double, label: String) {
    val color = when {
        prob >= 70 -> Color(0xFF4CAF50)
        prob in 40.0..69.9 -> Color(0xFFC47500)
        else -> Color(0xFFF44336)
    }

    Text(
        text = "$label ${prob.roundToInt()}%",
        style = MaterialTheme.typography.headlineSmall,
        color = color
    )
}
