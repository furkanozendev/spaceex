package com.spaceex.feature.detail.domain.di

import com.spaceex.feature.detail.domain.usecase.GetRocketDetailUseCase
import org.koin.dsl.module


val detailDomainModule = module {
    factory { GetRocketDetailUseCase(repository = get()) }
}