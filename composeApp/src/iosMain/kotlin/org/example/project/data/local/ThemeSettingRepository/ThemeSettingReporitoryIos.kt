package org.example.project.data.local.ThemeSettingRepository


import androidx.compose.ui.graphics.Color
import org.example.project.data.model.ThemeSettings
import org.example.project.ui.theme.MyThemeColor
import platform.Foundation.NSUserDefaults
class IosThemeSettingsRepository : ThemeSettingsRepository {
    private val defaults = NSUserDefaults.standardUserDefaults
    private val DARK_KEY = "dark_mode"
    private val THEME_KEY = "theme_color"

    override suspend fun save(settings: ThemeSettings) {
        defaults.setBool(settings.isDark, DARK_KEY)
        defaults.setObject(settings.theme.name, THEME_KEY)
    }

    override suspend fun load(): ThemeSettings {
        val isDark = defaults.boolForKey(DARK_KEY)
        val themeName = defaults.stringForKey(THEME_KEY)
        val theme = if (themeName != null) MyThemeColor.valueOf(themeName) else MyThemeColor.GREEN
        return ThemeSettings(isDark, theme)
    }
}

actual fun getThemeSettingsRepository(): ThemeSettingsRepository = IosThemeSettingsRepository()
