package com.spaceex

import androidx.compose.ui.window.ComposeUIViewController
import com.spaceex.di.initKoin

@Suppress("FunctionNaming")
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    MainScreen()
}
