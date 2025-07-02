package org.example.project.data.di

import org.example.project.data.fakeData.FakePlantRepository
import org.example.project.data.local.PlantHistoryRepository
import org.example.project.data.local.PlantHistoryRepositoryImpl
import org.example.project.data.local.ThemeSettingRepository.getThemeSettingsRepository
import org.example.project.data.local.roomDataBase.AppDatabase
import org.example.project.data.remote.ApiService
import org.example.project.data.remote.PlantRepositoryInterface
import org.example.project.ui.viewModels.HealthInfoViewModel
import org.example.project.ui.viewModels.PlantInfoViewModel
import org.example.project.ui.viewModels.ThemeSettingsViewModel
import org.example.project.ui.viewModels.UploadImageViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.example.project.data.remote.PlantRepository

val appModule = module {

    singleOf(::ApiService)

    single { get<AppDatabase>().plantHistoryDao() }
    single { getThemeSettingsRepository() }
   // singleOf(::PlantRepository) bind PlantRepositoryInterface::class
     singleOf(::FakePlantRepository) bind PlantRepositoryInterface::class
    singleOf(::PlantHistoryRepositoryImpl) bind PlantHistoryRepository::class


    singleOf(::ThemeSettingsViewModel)
    singleOf(::UploadImageViewModel)
    singleOf(::HealthInfoViewModel)
    single { PlantInfoViewModel(
        repo = get(),
        historyRepository = get()
    )}




}