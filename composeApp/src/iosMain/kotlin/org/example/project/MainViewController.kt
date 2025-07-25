package org.example.project

import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.data.di.RoomFactory
import org.example.project.data.di.initKoin

fun MainViewController() = ComposeUIViewController {
    val factory = RoomFactory()
    initKoin(factory)

    App()
}