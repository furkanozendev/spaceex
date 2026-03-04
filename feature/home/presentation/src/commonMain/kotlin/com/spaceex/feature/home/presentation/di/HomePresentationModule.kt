package com.spaceex.feature.home.presentation.di

import com.spaceex.core.navigation.NavGraphProvider
import com.spaceex.feature.home.presentation.navigation.HomeProvider
import com.spaceex.feature.home.presentation.ui.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homePresentationModule = module {
    single<NavGraphProvider>(named("HomeProvider")) {
        HomeProvider()
    }

    viewModelOf(::HomeViewModel)
}