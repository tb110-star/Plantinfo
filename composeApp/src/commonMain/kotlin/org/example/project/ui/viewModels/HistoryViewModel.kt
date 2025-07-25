package org.example.project.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.local.healthHistoryRoomRepository.HealthHistoryRepository
import org.example.project.data.local.plantHistoryRoomRepository.PlantHistoryRepository

class HistoryViewModel(
    private val plantRepo: PlantHistoryRepository,
    private val healthRepo: HealthHistoryRepository
) : ViewModel() {

    //private val _uiState = MutableStateFlow(HistoryUiState.IDLE)
   // val uiState = _uiState.asStateFlow()

    private val _category = MutableStateFlow("All")
    val category = _category.asStateFlow()

    // Observes and holds the latest plant history data as StateFlow

    val plantHistory = plantRepo.getAllHistory()
    val healthHistory = healthRepo.getAllHealthHistory()
    fun setCategory(cat: String) {
        _category.value = cat
    }
    fun deletePlant(id: String) {
        viewModelScope.launch {
            plantRepo.deleteById(id)
        }
    }
    fun deleteHealth(id: String) {

        viewModelScope.launch {
            healthRepo.deleteById(id)
        }
    }
}
