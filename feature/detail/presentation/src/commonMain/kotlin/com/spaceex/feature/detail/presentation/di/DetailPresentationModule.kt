package com.spaceex.feature.detail.presentation.di

import com.spaceex.core.navigation.NavGraphProvider
import com.spaceex.feature.detail.presentation.navigation.DetailProvider
import com.spaceex.feature.detail.presentation.ui.DetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val detailPresentationModule = module {
    single<NavGraphProvider>(named("DetailProvider")) {
        DetailProvider()
    }

    viewModelOf(::DetailViewModel)
}