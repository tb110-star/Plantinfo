package org.example.project.data.di

import org.example.project.data.fakeData.FakePlantRepository
import org.example.project.data.remote.PlantRepository
import org.example.project.ui.viewModels.PlantInfoViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    single<PlantRepository> { FakePlantRepository() }
   // single { PlantInfoViewModel(get())}
    factoryOf(::PlantInfoViewModel)
}