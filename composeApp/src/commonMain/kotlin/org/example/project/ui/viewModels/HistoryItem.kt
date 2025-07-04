package org.example.project.ui.viewModels

import org.example.project.data.local.roomModel.HealthHistoryEntity
import org.example.project.data.local.roomModel.PlantHistoryEntity

// Represents a sealed hierarchy of history items, combining both plant and health records
sealed class HistoryItem {
    data class PlantItem(val plant: PlantHistoryEntity) : HistoryItem()
    data class HealthItem(val health: HealthHistoryEntity) : HistoryItem()
}