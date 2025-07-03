package org.example.project.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.local.healthHistoryRoomRepository.HealthHistoryRepository
import org.example.project.data.local.roomModel.HealthHistoryEntity
import org.example.project.data.local.roomModel.toHealthHistory
import org.example.project.data.model.DiseaseSuggestion
import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.model.Questions
import org.example.project.data.model.RequestModel
import org.example.project.data.remote.ApiRepositoryInterface

class HealthInfoViewModel (
    private val repo: ApiRepositoryInterface,
    private val historyRepository: HealthHistoryRepository

):ViewModel() {

    private val _healthInfo = MutableStateFlow<HealthAssessmentResponse?>(null)
    val healthInfo = _healthInfo.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _serverImageUrl = MutableStateFlow<String?>(null)
    val serverImageUrl = _serverImageUrl.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()
    private val _selectedSuggestion = MutableStateFlow<String?>(null)
    val selectedSuggestion = _selectedSuggestion.asStateFlow()

    fun onQuestionAnswered(isYes: Boolean, question: Questions) {

        val selectedName = if (isYes) {
            question.options?.yes?.name
        } else {
            question.options?.no?.name
        } ?: ""
        _selectedSuggestion.value = selectedName
        println("Selected Suggestion: $selectedName")
    }
    fun loadHealthInfo(request: RequestModel) {
        println("loadHealthInfo called with request: $request")

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val result = repo.getHealthAssessment(request)
                println(" Health assessment result: $result")

                _healthInfo.value = result
                _serverImageUrl.value = result.input.images.firstOrNull()

                println("Emitting health info: $healthInfo")

            } catch (e: Exception) {
                println("Error loading health info: $e")
                _errorMessage.value = e.message
                _healthInfo.value = null
            } finally {
                _isLoading.value = false
                println(" loadHealthInfo finished - isLoading: ${_isLoading.value}")

            }
        }
    }
    fun refreshUI() {
        _healthInfo.value = _healthInfo.value
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
    fun setUploadedImageInfo(localPath: String, serverUrl: String?) {
        _serverImageUrl.value = serverUrl
    }

    fun saveConfirmedSuggestion(suggestion: DiseaseSuggestion) {
        viewModelScope.launch {
            try {
                println("saveConfirmedSuggestion called with serverImageUrl=${serverImageUrl.value}")

                val combinedId = "${suggestion.id}_${serverImageUrl.value}"
                val entity = suggestion.toHealthHistory(
                    combinedId = combinedId,
                    healthStatus = healthInfo.value?.result?.isHealthy?.binary.toString(),
                    selectedSuggestion = selectedSuggestion.value,
                    serverImageUrl = _serverImageUrl.value
                )
                println("HealthHistoryEntity: $entity")

                historyRepository.saveHealthRecord(entity)
            } catch (e: Exception) {
                println("Error saving health history: $e")
            }
        }
    }

}