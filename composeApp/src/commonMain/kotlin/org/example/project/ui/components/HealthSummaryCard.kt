package org.example.project.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.data.model.HealthAssessmentResponse


@Composable
fun HealthSummaryCard(
    healthInfo: HealthAssessmentResponse,
    backgroundModifier: Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(backgroundModifier),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Is Healthy: ${healthInfo.result.isHealthy.binary} (prob: ${healthInfo.result.isHealthy.probability})")
            Text("Is Plant: ${healthInfo.result.isPlant.binary} (prob: ${healthInfo.result.isPlant.probability})")
        }
    }
}

