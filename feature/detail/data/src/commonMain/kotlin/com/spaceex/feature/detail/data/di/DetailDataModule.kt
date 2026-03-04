package com.spaceex.feature.detail.data.di

import com.spaceex.feature.detail.data.RocketRepositoryImpl
import com.spaceex.feature.detail.data.network.RocketApi
import com.spaceex.feature.detail.domain.repository.RocketRepository
import org.koin.dsl.module

val detailDataModule = module {
    single { RocketApi(httpClient = get()) }

    single<RocketRepository> { RocketRepositoryImpl(api = get()) }
}