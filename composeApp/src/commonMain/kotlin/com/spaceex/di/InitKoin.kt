package com.spaceex.di

import com.spaceex.core.cache.di.cacheModule
import com.spaceex.core.navigation.navigationModule
import com.spaceex.core.network.di.networkModule
import com.spaceex.feature.detail.data.di.detailDataModule
import com.spaceex.feature.detail.domain.di.detailDomainModule
import com.spaceex.feature.detail.presentation.di.detailPresentationModule
import com.spaceex.feature.home.data.di.homeDataModule
import com.spaceex.feature.home.domain.di.homeDomainModule
import com.spaceex.feature.home.presentation.di.homePresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)

        modules(
            cacheModule,
            navigationModule,
            networkModule,
            homeDataModule,
            homeDomainModule,
            homePresentationModule,
            detailDataModule,
            detailDomainModule,
            detailPresentationModule
        )
    }
}
