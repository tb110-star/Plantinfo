package org.example.project.ui.viewModels

//import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.model.PlantIdentificationResult
import org.example.project.data.remote.PlantRepository
//import com.hoc081098.kmp.viewmodel.ViewModel
class PlantInfoViewModel(
    private val repo: PlantRepository
) : ViewModel() {
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
}