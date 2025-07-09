package org.example.project.data.local.healthHistoryRoomRepository

import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.Daos.HealthHistoryDao
import org.example.project.data.local.roomModel.HealthHistoryEntity

class HealthHistoryRepositoryImpl(
    private val dao: HealthHistoryDao
):HealthHistoryRepository {
    override suspend fun saveHealthRecord(health: HealthHistoryEntity) {
        dao.insertHealthRecord(health)
    }

    override fun getAllHealthHistory(): Flow<List<HealthHistoryEntity>> = dao.getAllHealthRecords()
    override suspend fun deleteById(id: String) {
        dao.deleteById(id)
    }
}