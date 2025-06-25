package org.example.project.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import org.example.project.data.model.PlantIdentificationResult
import org.example.project.data.model.Suggestions
import org.example.project.data.remote.PlantRepository
//import com.hoc081098.kmp.viewmodel.ViewModel
class PlantInfoViewModel(
    private val repo: PlantRepository
) : ViewModel() {
    private val _isShowingAddSheet = MutableStateFlow(false)
    val isShowingAddSheet = _isShowingAddSheet.asStateFlow()
    private val _isDone = MutableStateFlow(false)
    val isDone = _isDone.asStateFlow()
    private val _selectedImagePath = MutableStateFlow<String?>(null)
    val selectedImagePath = _selectedImagePath.asStateFlow()
    private val _plantInfo = MutableStateFlow<PlantIdentificationResult?>(null)
    val plantInfo = _plantInfo.asStateFlow()
    fun loadPlantInfo() {
        viewModelScope.launch {
            try {
val result = repo.getPlantIdentification()
                _plantInfo.value = result
            } catch (e: Exception){
                println("Error loading plant info: $e")
                _plantInfo.value = null
            }
        }
    }
    fun enableAddSheet(){
        _isShowingAddSheet.value = true
    }
    fun disableAddSheet(){
        _isShowingAddSheet.value = false
    }
    private val _selectedSuggestion = MutableStateFlow<Suggestions?>(null)
    val selectedSuggestion = _selectedSuggestion.asStateFlow()

    fun selectSuggestion(suggestion: Suggestions) {
        _selectedSuggestion.value = suggestion
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




