package com.spaceex.feature.home.domain.di

import com.spaceex.feature.home.domain.usecase.GetLaunchesUseCase
import org.koin.dsl.module

val homeDomainModule = module {
    factory { GetLaunchesUseCase(repository = get()) }
}
