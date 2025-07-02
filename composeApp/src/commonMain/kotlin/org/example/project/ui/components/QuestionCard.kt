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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kotlinproject.composeapp.generated.resources.Res
@Composable
fun QuestionCard(
    question: Questions,
    selectedSuggestion: String?,
    onAnswerSelected: (Boolean) -> Unit
) {
    val selectedColor = Color(0xE8651313).copy(alpha = 0.2f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = question.text ?: "Unknown question",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // YES Button
                Button(
                    onClick = { onAnswerSelected(true) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedSuggestion == "YES") selectedColor else selectedColor.copy(alpha = 0.2f)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = "Yes",
                        color =  MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(16.dp))

                }

                // NO Button
                Button(
                    onClick = { onAnswerSelected(false) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedSuggestion == "NO") selectedColor else selectedColor.copy(alpha = 0.2f)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = "No",
                        color =  MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium

                    )
                }
            }

            selectedSuggestion?.let { suggestionName ->
                Spacer(Modifier.height(16.dp))
                Text(
                    text = suggestionName,
                    style = MaterialTheme.typography.bodyLarge,
                    color =  MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
