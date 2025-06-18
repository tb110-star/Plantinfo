package org.example.project.data.model

import org.example.project.ui.theme.MyThemeColor
import kotlinx.serialization.Serializable

@Serializable
data class ThemeSettings(
    val isDark: Boolean = true,
    val theme: MyThemeColor = MyThemeColor.GREEN
)