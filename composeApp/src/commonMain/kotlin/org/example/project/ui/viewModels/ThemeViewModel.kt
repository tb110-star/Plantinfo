package org.example.project.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.data.local.ThemeSettingRepository.ThemeSettingsRepository
import org.example.project.data.model.ThemeSettings
import org.example.project.ui.theme.MyThemeColor

class ThemeSettingsViewModel(
    private val repository: ThemeSettingsRepository
) : ViewModel() {
    private val _settings = MutableStateFlow(ThemeSettings())
    val settings: StateFlow<ThemeSettings> = _settings

    init {
        viewModelScope.launch {
            _settings.value = repository.load()
        }
    }

    fun setDark(isDark: Boolean) {
        val new = _settings.value.copy(isDark = isDark)
        _settings.value = new
        viewModelScope.launch { repository.save(new) }
    }

    fun setTheme(theme: MyThemeColor) {
        val new = _settings.value.copy(theme = theme)
        _settings.value = new
        viewModelScope.launch { repository.save(new) }
    }



    private fun update(newSettings: ThemeSettings) {
        _settings.value = newSettings
        CoroutineScope(Dispatchers.Default).launch {
            repository.save(newSettings)
        }
    }
}
