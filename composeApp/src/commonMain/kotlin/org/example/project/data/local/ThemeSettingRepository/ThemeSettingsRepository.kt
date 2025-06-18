package org.example.project.data.local.ThemeSettingRepository

import org.example.project.data.model.ThemeSettings

interface ThemeSettingsRepository{
    suspend fun save(setting: ThemeSettings)
    suspend fun load(): ThemeSettings
}
expect fun getThemeSettingsRepository(): ThemeSettingsRepository