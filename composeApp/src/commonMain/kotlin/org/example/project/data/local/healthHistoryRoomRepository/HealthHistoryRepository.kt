package org.example.project.data.local.healthHistoryRoomRepository

import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.roomModel.HealthHistoryEntity

interface HealthHistoryRepository {
    suspend fun saveHealthRecord(health: HealthHistoryEntity)
    fun getAllHealthHistory():Flow<List<HealthHistoryEntity>>
}