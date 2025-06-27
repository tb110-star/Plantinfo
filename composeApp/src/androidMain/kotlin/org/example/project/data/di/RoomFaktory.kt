package org.example.project.data.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.example.project.data.local.roomDataBase.AppDatabase
import org.example.project.data.local.roomDataBase.DB_FILE_NAME
import org.koin.androidx.scope.FragmentScopeArchetype

actual class RoomFactory(
    private val app: Application,
) {
    actual fun createRoomDatabase(): AppDatabase {
        val dbFile = app.getDatabasePath(DB_FILE_NAME)
        return Room
            .databaseBuilder<AppDatabase>(
                context = app,
                name = dbFile.absolutePath,
            )
            //.setDriver(BundledSQLiteDriver())
            .openHelperFactory(FrameworkSQLiteOpenHelperFactory())
            .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}