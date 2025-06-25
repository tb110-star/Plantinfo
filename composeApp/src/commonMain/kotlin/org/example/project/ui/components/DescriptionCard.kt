package org.example.project.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.TextButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
@Composable
fun ExpandableInfoCard(
    title: String,
    icon: ImageVector,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    shortDescription: String,
    fullDescription: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    headerBackgroundColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
) {
    var expanded by remember { mutableStateOf(false) }

    val transition by animateDpAsState(
        targetValue = if (expanded) 360.dp else 200.dp,
        label = "CardHeight"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { expanded = !expanded }
            .animateContentSize(), // فقط همین کافیست
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            // 🟩 Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerBackgroundColor)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 12.dp)
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            // 📝 توضیحات
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = shortDescription,
                    style = MaterialTheme.typography.bodyLarge
                )

                if (expanded) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = fullDescription,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(if (expanded) "Show less" else "Learn more")
                }
            }
        }
    }

}


 */
@Composable
fun ExpandableInfoCard(
    title: String,
    icon: ImageVector,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    shortDescription: String,
    remainingDescription: String, // فقط ادامه توضیح
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    headerBackgroundColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
) {
    var expanded by remember { mutableStateOf(false) }
    val hasMore = remainingDescription.isNotBlank()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { expanded = !expanded }
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            // 🔷 Header with Icon and Title
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerBackgroundColor)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier
                            .size(44.dp)
                            .padding(end = 12.dp)
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = MaterialTheme.typography.titleLarge.fontSize * 1f,
                            fontWeight = FontWeight.Normal
                        )
                    )

                }
            }

            // 📝 Description
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ){
                Text(
                    text = shortDescription,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize * 1.1f,
                        fontFamily = FontFamily.Serif
                    )
                )

                if (expanded && hasMore) {
                  //  Spacer(modifier = Modifier.height(12.dp))
                  //  Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = remainingDescription,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize * 1.1f,
                            fontFamily = FontFamily.Serif
                        )
                    )
                }

                if (hasMore) {
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(if (expanded) "Show less" else "Learn more")
                    }
                }
            }
        }
    }
}
