package org.example.project.data.di

class AppContainer (
    private val factory: RoomFactory,
)
{
    val roomDatabase by lazy { factory.createRoomDatabase() }
}