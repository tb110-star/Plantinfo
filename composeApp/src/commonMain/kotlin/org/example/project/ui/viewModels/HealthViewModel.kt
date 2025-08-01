package org.example.project.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.local.healthHistoryRoomRepository.HealthHistoryRepository
import org.example.project.data.local.roomModel.toHealthHistory
import org.example.project.data.model.DiseaseSuggestion
import org.example.project.data.model.HealthAssessmentResponse
import org.example.project.data.model.Questions
import org.example.project.data.model.RequestModel
import org.example.project.data.remote.ApiRepositoryInterface

class HealthViewModel (
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
    private val _image = mutableStateOf<ByteArray?>(null)
    val image: State<ByteArray?> = _image
    private val _isImageDialogOpen = MutableStateFlow(false)
    val isImageDialogOpen: StateFlow<Boolean> = _isImageDialogOpen

    fun openImageDialog() {
        _isImageDialogOpen.value = true
    }

    fun closeImageDialog() {
        _isImageDialogOpen.value = false
    }
    fun setImage(newImage: ByteArray?) {
        _image.value = newImage
    }
    // Loads health assessment data from API
    fun loadHealthInfo(request: RequestModel) {
        viewModelScope.launch {
            _healthInfo.value = null
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val result = repo.getHealthAssessment(request)
                result.fold(
                    onSuccess = { response ->
                        _healthInfo.value = response
                        _serverImageUrl.value = response.input.images.firstOrNull()
                    },
                    onFailure = { error ->
                        println("Error: ${error.message}")
                        _healthInfo.value = null
                        _errorMessage.value = error.message ?: "An unknown error occurred"
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Saves a confirmed disease suggestion to the history database
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

    // Handles user answer to a question and updates the selected suggestion
    fun onQuestionAnswered(isYes: Boolean, question: Questions) {

        val selectedName = if (isYes) {
            question.options?.yes?.name
        } else {
            question.options?.no?.name
        } ?: ""
        _selectedSuggestion.value = selectedName
        println("Selected Suggestion: $selectedName")
    }

    fun clear() {
        _healthInfo.value = null
        _isLoading.value = false
        _serverImageUrl.value = null
        _selectedSuggestion.value = null
        _errorMessage.value = null

    }

}