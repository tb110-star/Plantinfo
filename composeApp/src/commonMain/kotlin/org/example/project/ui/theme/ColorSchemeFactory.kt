package org.example.project.ui.theme


import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme

fun createColorSchemes(palette: MaterialThemeColorsPalette): Pair<ColorScheme, ColorScheme> {
    val light = lightColorScheme(
        primary = palette.primaryLight,
        onPrimary = palette.onPrimaryLight,
        background = palette.backgroundLight,
        onBackground = palette.onBackgroundLight,
    )
    val dark = darkColorScheme(
        primary = palette.primaryDark,
        onPrimary = palette.onPrimaryDark,
        background = palette.backgroundDark,
        onBackground = palette.onBackgroundDark,
    )
    return Pair(light, dark)
}

fun getSelectedThemeColors(selected: MyThemeColor): Pair<ColorScheme, ColorScheme> =
    when (selected) {
        MyThemeColor.GREEN -> createColorSchemes(GreenColorBase)
        // MyThemeColor.ORANGE -> createColorSchemes(OrangeColorBase)
        // ...
    }
