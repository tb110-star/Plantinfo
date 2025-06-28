package org.example.project.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.remote.PlantRepository

class HealthInfoViewModel (
    private val repo: PlantRepository
    ):ViewModel() {
    private val _healthInfo = MutableStateFlow<HealthAssessmentResponse?>(null)
    val healthInfo = _healthInfo.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun loadHealthInfo() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val result = repo.getHealthAssessment()
                _healthInfo.value = result
            } catch (e: Exception) {
                println("Error loading health info: $e")
                _errorMessage.value = e.message
                _healthInfo.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearHealthInfo() {
        _healthInfo.value = null
    }

    fun splitTextSmart(text: String, maxLength: Int = 300): Pair<String, String> {
        if (text.length <= maxLength) return text to ""

        val breakpoint = text.lastIndexOfAny(charArrayOf('.', '\n'), maxLength)
            .takeIf { it != -1 } ?: maxLength

        val firstPart = text.substring(0, breakpoint + 1).trimEnd()
        val secondPart = text.substring(breakpoint + 1).trimStart()

        return firstPart to secondPart
    }
}