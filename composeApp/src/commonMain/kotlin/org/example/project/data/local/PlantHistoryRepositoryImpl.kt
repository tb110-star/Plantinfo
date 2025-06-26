package org.example.project.data.local

import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.roomDataBase.PlantHistoryDao
import org.example.project.data.local.roomModel.PlantHistoryEntity

class PlantHistoryRepositoryImpl(
    private val dao: PlantHistoryDao
) : PlantHistoryRepository {
    override suspend fun saveToHistory(plant: PlantHistoryEntity) {
        dao.insert(plant)
    }

    override fun getAllHistory(): Flow<List<PlantHistoryEntity>> = dao.getAllAsFlow()

    override suspend fun getByIds(ids: List<Long>) = dao.loadAll(ids)

  //  override suspend fun getMappedByIds(ids: List<Long>) = dao.loadMapped(ids)

   // override suspend fun count(): Int = dao.count()
}
