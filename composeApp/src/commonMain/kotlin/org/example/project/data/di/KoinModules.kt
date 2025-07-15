package org.example.project.data.di

import org.example.project.data.local.plantHistoryRoomRepository.PlantHistoryRepository
import org.example.project.data.local.plantHistoryRoomRepository.PlantHistoryRepositoryImpl
import org.example.project.data.local.ThemeSettingRepository.getThemeSettingsRepository
import org.example.project.data.local.healthHistoryRoomRepository.HealthHistoryRepository
import org.example.project.data.local.healthHistoryRoomRepository.HealthHistoryRepositoryImpl
import org.example.project.data.local.roomDataBase.AppDatabase
import org.example.project.data.remote.ApiService
import org.example.project.data.fakeData.FakeApiRepository
import org.example.project.data.remote.ApiRepositoryInterface
import org.example.project.data.remote.ApiRepository

import org.example.project.ui.viewModels.HealthViewModel
import org.example.project.ui.viewModels.HistoryViewModel
import org.example.project.ui.viewModels.HomeViewModel
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
  //  singleOf(::ApiRepository) bind ApiRepositoryInterface::class
    singleOf(::FakeApiRepository) bind ApiRepositoryInterface::class
    singleOf(::PlantHistoryRepositoryImpl) bind PlantHistoryRepository::class
    singleOf(::HealthHistoryRepositoryImpl) bind HealthHistoryRepository::class
    singleOf(::ThemeSettingsViewModel)
    singleOf(::UploadImageViewModel)
    singleOf(::HealthViewModel)
    single { HomeViewModel(
        repo = get(),
        historyRepository = get()
    )}
    single { HistoryViewModel(
        plantRepo = get(),
        healthRepo  = get()
    )}


}