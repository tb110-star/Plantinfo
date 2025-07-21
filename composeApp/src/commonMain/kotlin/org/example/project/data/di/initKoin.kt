package org.example.project.data.di

import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(
    roomFactory: RoomFactory
) {
    val factoryModule = module {
        single<RoomFactory> { roomFactory }
        single { AppContainer(get()) }
        single { get<AppContainer>().roomDatabase }
    }

    startKoin {
        modules(
            factoryModule,
            appModule
        )
    }
}
