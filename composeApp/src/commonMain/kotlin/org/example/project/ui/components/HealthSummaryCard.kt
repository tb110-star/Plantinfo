package org.example.project.ui.components
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.data.model.HealthAssessmentResponse
import androidx.compose.foundation.layout.*
@Composable
fun HealthSummaryCard(
    healthInfo: HealthAssessmentResponse
) {
    val healthyProb = healthInfo.result.isHealthy.probability * 100
    val plantProb = healthInfo.result.isPlant.probability * 100
   // val healthyProb = 70.0

    val backgroundColor = when {
        healthyProb >= 70 -> Color(0xFF4CAF50).copy(alpha = 0.2f)
        healthyProb in 40.0..69.9 -> Color(0xF8FAE97D).copy(alpha = 0.2f)
        else -> Color(0xFFF44336).copy(alpha = 0.2f)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 32.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
           // StatusEmoji(prob = 40.0, label = "🩺")
            StatusEmoji(prob = healthyProb, label = "🩺")
            Spacer(modifier = Modifier.width(24.dp))
            StatusEmoji(prob = plantProb, label = "🌿")
          //  StatusEmoji(prob = 40.0, label = "🌿")
        }
    }
}
