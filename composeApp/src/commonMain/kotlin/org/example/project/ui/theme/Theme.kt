package org.example.project.ui.theme


import androidx.compose.runtime.Composable

@Composable
expect fun AppTheme(
    darkTheme: Boolean,
    selectedTheme: MyThemeColor,
    content: @Composable () -> Unit
)
