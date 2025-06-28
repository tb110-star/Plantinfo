package org.example.project.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.data.model.Questions

@Composable
fun QuestionCard(
    question: Questions,
    backgroundModifier: Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(backgroundModifier),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(question.text, style = MaterialTheme.typography.titleMedium)
            question.translation?.let {
                Text(it, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Text("Yes -> ${question.options.yes.name}")
            Text("No -> ${question.options.no.name}")
        }
    }
}
