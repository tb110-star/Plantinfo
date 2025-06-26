package org.example.project.data.local.roomDataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.roomModel.PlantHistoryEntity

@Dao
interface PlantHistoryDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plantHistory: PlantHistoryEntity)

    @Query("SELECT * FROM plantHistory")
    fun getAllAsFlow(): Flow<List<PlantHistoryEntity>>

    @Query("SELECT * FROM plantHistory WHERE id in (:ids)")
    suspend fun loadAll(ids: List<Long>): List<PlantHistoryEntity>
/*
    @Query("SELECT COUNT(*) as count FROM plantHistory")
    suspend fun count(): Int





    @Query("SELECT * FROM plantHistory WHERE id in (:ids)")
    suspend fun loadMapped(ids: List<Long>): Map<
            @MapColumn(columnName = "id")
            Long,
            PlantHistoryEntity,
            >

 */
}