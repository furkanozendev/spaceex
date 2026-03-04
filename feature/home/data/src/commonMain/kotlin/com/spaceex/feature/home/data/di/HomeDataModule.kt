package com.spaceex.feature.home.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.spaceex.core.cache.DatabaseFactory
import com.spaceex.feature.home.data.LaunchRepositoryImpl
import com.spaceex.feature.home.data.cache.AppDatabaseConstructor
import com.spaceex.feature.home.data.cache.LaunchDatabase
import com.spaceex.feature.home.data.network.LaunchApi
import com.spaceex.feature.home.domain.repository.LaunchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val homeDataModule = module {
    single<LaunchDatabase> {
        val factory = get<DatabaseFactory>()

        factory.createRoomDatabase<LaunchDatabase>("launches.db") {
            AppDatabaseConstructor.initialize()
        }
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    single { get<LaunchDatabase>().launchDao() }

    single { LaunchApi(httpClient = get()) }

    single<LaunchRepository> { LaunchRepositoryImpl(api = get(), dao = get()) }
}