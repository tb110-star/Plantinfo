package org.example.project.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    selectedTheme: MyThemeColor,
    content: @Composable () -> Unit
) {
    val (lightScheme, darkScheme) = getSelectedThemeColors(selectedTheme)
    val colorScheme = if (darkTheme) darkScheme else lightScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
