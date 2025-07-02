package org.example.project.data.local.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.roomModel.HealthHistoryEntity

@Dao
interface HealthHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthRecord(record: HealthHistoryEntity)

    @Query("SELECT * FROM health_history ORDER BY id DESC")
    fun getAllHealthRecords(): Flow<List<HealthHistoryEntity>>
}