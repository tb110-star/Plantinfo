package org.example.project.data.local.ThemeSettingRepository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import org.example.project.data.model.ThemeSettings
import org.example.project.ui.theme.MyThemeColor
lateinit var appContext: Context

private val Context.dataStore by preferencesDataStore("theme_settings")

class AndroidThemeSettingsRepository(private val context: Context) : ThemeSettingsRepository {
    companion object {
        private val DARK_KEY = booleanPreferencesKey("dark_mode")
        private val THEME_KEY = stringPreferencesKey("theme_color")
    }
    override suspend fun save(settings: ThemeSettings) {
        context.dataStore.edit { prefs ->
            prefs[DARK_KEY] = settings.isDark
            prefs[THEME_KEY] = settings.theme.name
        }
    }

    override suspend fun load(): ThemeSettings {
        val prefs = context.dataStore.data.first()
        val isDark = prefs[DARK_KEY] ?: false
        val theme = prefs[THEME_KEY]?.let { MyThemeColor.valueOf(it) } ?: MyThemeColor.GREEN
        return ThemeSettings(isDark, theme)
    }
}

actual fun getThemeSettingsRepository(): ThemeSettingsRepository =
    AndroidThemeSettingsRepository(appContext)