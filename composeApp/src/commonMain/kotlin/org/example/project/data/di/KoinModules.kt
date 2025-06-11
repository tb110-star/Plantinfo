package org.example.project.data.di

import org.example.project.data.fakeData.FakePlantRepository
import org.example.project.data.remote.PlantRepository
import org.koin.dsl.module

val appModule = module {
    single<PlantRepository> { FakePlantRepository() }
}