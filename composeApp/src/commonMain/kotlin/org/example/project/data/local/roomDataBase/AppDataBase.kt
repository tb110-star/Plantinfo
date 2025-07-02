package org.example.project.data.local.roomDataBase

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.project.data.local.roomModel.PlantHistoryEntity
import androidx.room.RoomDatabaseConstructor
import org.example.project.data.local.Daos.HealthHistoryDao
import org.example.project.data.local.Daos.PlantHistoryDao
import org.example.project.data.local.roomModel.HealthHistoryEntity

@Database(entities = [PlantHistoryEntity::class, HealthHistoryEntity::class], version = 2)
@ConstructedBy(AppDatabaseConstructor::class)


abstract class AppDatabase : RoomDatabase() {
    abstract fun healthHistoryDao(): HealthHistoryDao
    abstract fun plantHistoryDao(): PlantHistoryDao
}
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

internal const val DB_FILE_NAME = "plantHistory.db"
