package org.example.project.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.data.local.healthHistoryRoomRepository.HealthHistoryRepository
import org.example.project.data.local.plantHistoryRoomRepository.PlantHistoryRepository

class HistoryViewModel(
    private val plantRepo: PlantHistoryRepository,
    private val healthRepo: HealthHistoryRepository
) : ViewModel() {

    private val _category = MutableStateFlow("All")
    val category = _category.asStateFlow()

    val plantHistory = plantRepo.getAllHistory().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val healthHistory = healthRepo.getAllHealthHistory().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun setCategory(cat: String) {
        _category.value = cat
    }

    fun deletePlant(id: Long) {
        viewModelScope.launch {
            plantRepo.getByIds(listOf(id)).firstOrNull()?.let {
                plantRepo.saveToHistory(it.copy(isConfirmed = false)) // یا dao.delete()
            }
        }
    }

    fun deleteHealth(id: Long) {
        viewModelScope.launch {
          //  healthRepo.getByIds(listOf(id)).firstOrNull()?.let {
            //    healthRepo.saveHealthRecord(it.copy(isConfirmed = false)) // یا dao.delete()
          //  }
        }
    }
}
