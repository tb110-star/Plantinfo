package org.example.project.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.local.plantHistoryRoomRepository.PlantHistoryRepository
import org.example.project.data.local.roomModel.toPlantHistory
import org.example.project.data.model.PlantIdentificationResult
import org.example.project.data.model.RequestModel
import org.example.project.data.model.Suggestions
import org.example.project.data.remote.ApiRepositoryInterface

class HomeViewModel(
    private val repo: ApiRepositoryInterface,
    private val historyRepository: PlantHistoryRepository

) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isShowingAddSheet = MutableStateFlow(false)
    val isShowingAddSheet = _isShowingAddSheet.asStateFlow()

    private val _plantInfo = MutableStateFlow<PlantIdentificationResult?>(null)
    val plantInfo = _plantInfo.asStateFlow()

    private val _serverImageUrl = MutableStateFlow<String?>(null)
    val serverImageUrl = _serverImageUrl.asStateFlow()

    private val _selectedSuggestion = MutableStateFlow<Suggestions?>(null)
    val selectedSuggestion = _selectedSuggestion.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()
    private val _image = mutableStateOf<ByteArray?>(null)
    val image: State<ByteArray?> = _image

    fun setImage(newImage: ByteArray?) {
        _image.value = newImage
    }

    fun enableAddSheet(){
        _isShowingAddSheet.value = true
    }
    fun disableAddSheet(){
        _isShowingAddSheet.value = false
    }

    // Updates the selected suggestion
    fun selectSuggestion(suggestion: Suggestions) {
        _selectedSuggestion.value = suggestion
    }

    // Loads plant info by sending a request to the API
    fun loadPlantInfo(request: RequestModel) {
        viewModelScope.launch {
            _plantInfo.value = null
            _isLoading.value = true
            try {
                val result = repo.getPlantIdentification(request)
                result.fold(
                    onSuccess = { response ->
                        _plantInfo.value = response
                        _serverImageUrl.value = response.input.images.firstOrNull()
                    },
                    onFailure = { error ->
                        println("Error: ${error.message}")
                        _plantInfo.value = null
                        _errorMessage.value = error.message ?: "An unknown error occurred"
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    // Saves the selected suggestion to the plant history database
    fun saveToPlantHistory(
        suggestion: Suggestions,
        serverImageUrl: String?,
        onResult: (Boolean) -> Unit
    )  {
        viewModelScope.launch {
            try {
                println("saveToPlantstory called with  serverImageUrl=$serverImageUrl")
                val combinedId = "${suggestion.id}_${serverImageUrl}"
                val entity = suggestion.toPlantHistory(
                    combinedId = combinedId,
                    serverImageUrl = serverImageUrl
                ).copy(isConfirmed = true)
                println("PlantHistoryEntity: $entity")

                historyRepository.saveToHistory(entity)
                println("Saved successfully: $entity")
                onResult(true)
            } catch (e: Exception) {
                println("Error saving Plant history: $e")
                onResult(false)

            }
        }
    }

    // Utility function to split long text into two parts without cutting off mid-sentence
    fun splitTextSmart(text: String, maxLength: Int = 300): Pair<String, String> {
        if (text.length <= maxLength) return text to ""

        val breakpoint = text.lastIndexOfAny(charArrayOf('.', '\n'), maxLength)
            .takeIf { it != -1 } ?: maxLength

        val firstPart = text.substring(0, breakpoint + 1).trimEnd()
        val secondPart = text.substring(breakpoint + 1).trimStart()

        return firstPart to secondPart
    }
    fun clear() {
        _plantInfo.value = null
        _isLoading.value = false
        _serverImageUrl.value = null
        _selectedSuggestion.value = null
        _errorMessage.value = null

    }

}




