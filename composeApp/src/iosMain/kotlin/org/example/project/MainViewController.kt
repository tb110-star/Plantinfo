package org.example.project

import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.data.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()

    App()
}