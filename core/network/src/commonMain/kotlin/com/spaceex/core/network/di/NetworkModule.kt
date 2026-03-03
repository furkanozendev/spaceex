package com.spaceex.core.network.di

import com.spaceex.core.network.client.KtorClientFactory
import com.spaceex.core.network.config.BuildConfig
import org.koin.dsl.module

val networkModule = module {
    single {
        KtorClientFactory.createClient(
            baseUrl = BuildConfig.BASE_URL,
            enableLogging = true // You can toggle this based on BuildVariant if you expose it
        )
    }
}