package com.spaceex

import androidx.compose.ui.window.ComposeUIViewController
import com.spaceex.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    MainScreen()
}