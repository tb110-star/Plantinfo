package org.example.project.data.di

import org.example.project.data.local.PlantHistoryRepository
import org.example.project.data.local.roomDataBase.AppDatabase


expect class RoomFactory {
    fun createRoomDatabase(): AppDatabase
}