package org.example.project.data.di

import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.data.di.initKoin
import org.example.project.App

fun MainViewController() = ComposeUIViewController {
    val factory = RoomFactory()
    initKoin(factory)

    App()
}
