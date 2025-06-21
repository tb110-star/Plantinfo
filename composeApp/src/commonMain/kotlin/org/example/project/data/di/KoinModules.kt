package org.example.project.data.di

import org.example.project.data.fakeData.FakePlantRepository
import org.example.project.data.local.ThemeSettingRepository.getThemeSettingsRepository
import org.example.project.data.permissionManager.PermissionBridge
import org.example.project.data.remote.PlantRepository
import org.example.project.data.uploadImage.uploadImageRepository.UploadImageRepository
import org.example.project.ui.viewModels.PlantInfoViewModel
import org.example.project.ui.viewModels.ThemeSettingsViewModel
import org.example.project.ui.viewModels.UploadImageViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    single<PlantRepository> { FakePlantRepository() }
    // single { PlantInfoViewModel(get())}
    factoryOf(::PlantInfoViewModel)
    single { getThemeSettingsRepository() }
    single { ThemeSettingsViewModel(get()) }
    single { PermissionBridge() }
    single { UploadImageViewModel(get()) }
    single { UploadImageRepository() }
}