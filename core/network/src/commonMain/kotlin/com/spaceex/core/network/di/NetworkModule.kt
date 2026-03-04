package com.spaceex.core.network.di

import com.spaceex.core.network.client.KtorClientFactory
import io.ktor.client.HttpClient
import org.koin.dsl.module

// Should be in Build Config in Production App
private const val BASE_URL = "api.spacexdata.com/v4"

val networkModule = module {
    single<HttpClient> {
        KtorClientFactory.createClient(
            baseUrl = BASE_URL,
            enableLogging = true
        )
    }
}
