package com.spaceex.core.network.di

import com.spaceex.core.network.client.KtorClientFactory
import com.spaceex.core.network.config.BuildConfig
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        KtorClientFactory.createClient(
            baseUrl = BuildConfig.BASE_URL,
            enableLogging = true // You can toggle this based on BuildVariant if you expose it
        )
    }
}
