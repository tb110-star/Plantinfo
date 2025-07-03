package org.example.project.ui.viewModels

import org.example.project.data.local.roomModel.HealthHistoryEntity
import org.example.project.data.local.roomModel.PlantHistoryEntity

sealed class HistoryItem {
    data class PlantItem(val plant: PlantHistoryEntity) : HistoryItem()
    data class HealthItem(val health: HealthHistoryEntity) : HistoryItem()
}