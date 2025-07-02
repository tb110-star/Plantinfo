package org.example.project.data.local.plantHistoryRoomRepository

import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.roomModel.PlantHistoryEntity

interface PlantHistoryRepository {
    suspend fun saveToHistory(plant: PlantHistoryEntity)
    fun getAllHistory(): Flow<List<PlantHistoryEntity>>
    suspend fun getByIds(ids: List<Long>): List<PlantHistoryEntity>
  //  suspend fun getMappedByIds(ids: List<Long>): Map<Long, PlantHistoryEntity>
  //  suspend fun count(): Int
}
