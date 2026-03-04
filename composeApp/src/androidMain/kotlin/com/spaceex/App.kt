package com.spaceex

import android.app.Application
import com.spaceex.di.initKoin
import org.koin.android.ext.koin.androidContext

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin { androidContext(this@App) }
    }
}
