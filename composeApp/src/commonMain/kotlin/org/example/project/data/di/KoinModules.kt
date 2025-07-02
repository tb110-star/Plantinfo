package org.example.project.data.di

import org.example.project.data.fakeData.FakePlantRepository
import org.example.project.data.local.plantHistoryRoomRepository.PlantHistoryRepository
import org.example.project.data.local.plantHistoryRoomRepository.PlantHistoryRepositoryImpl
import org.example.project.data.local.ThemeSettingRepository.getThemeSettingsRepository
import org.example.project.data.local.healthHistoryRoomRepository.HealthHistoryRepository
import org.example.project.data.local.healthHistoryRoomRepository.HealthHistoryRepositoryImpl

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

val appModule = module {

    singleOf(::ApiService)

    single { get<AppDatabase>().plantHistoryDao() }
    single{ get<AppDatabase>().healthHistoryDao() }
    single { getThemeSettingsRepository() }
   // singleOf(::PlantRepository) bind PlantRepositoryInterface::class
     singleOf(::FakePlantRepository) bind PlantRepositoryInterface::class
    singleOf(::PlantHistoryRepositoryImpl) bind PlantHistoryRepository::class
    singleOf(::HealthHistoryRepositoryImpl) bind HealthHistoryRepository::class
    singleOf(::ThemeSettingsViewModel)
    singleOf(::UploadImageViewModel)
    singleOf(::HealthInfoViewModel)
    single { PlantInfoViewModel(
        repo = get(),
        historyRepository = get()
    )}




}