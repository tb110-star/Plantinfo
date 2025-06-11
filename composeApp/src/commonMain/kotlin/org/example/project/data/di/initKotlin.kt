package org.example.project.data.di

import org.koin.core.context.startKoin
fun initKoin(){
    startKoin{
        modules(appModule)
    }
}