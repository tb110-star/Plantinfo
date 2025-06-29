package org.example.project.data.di

import org.example.project.data.fakeData.FakePlantRepository
import org.example.project.data.local.PlantHistoryRepository
import org.example.project.data.local.PlantHistoryRepositoryImpl
import org.example.project.data.local.ThemeSettingRepository.getThemeSettingsRepository
import org.example.project.data.local.roomDataBase.AppDatabase
import org.example.project.data.local.roomDataBase.AppDatabaseConstructor
import org.example.project.data.remote.PlantRepository
import org.example.project.ui.viewModels.HealthInfoViewModel
import org.example.project.ui.viewModels.PlantInfoViewModel
import org.example.project.ui.viewModels.ThemeSettingsViewModel
import org.example.project.ui.viewModels.UploadImageViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    single { get<AppDatabase>().plantHistoryDao() }
    single<PlantHistoryRepository> { PlantHistoryRepositoryImpl(get()) }
    single<PlantRepository> { FakePlantRepository() }
     single { PlantInfoViewModel(
         repo = get(),
         historyRepository = get()
     )}
   // factoryOf(::PlantInfoViewModel)
    single { getThemeSettingsRepository() }
    single { ThemeSettingsViewModel(get()) }
    single { UploadImageViewModel() }
    single { HealthInfoViewModel(get()) }


}