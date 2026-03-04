package com.spaceex.feature.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.spaceex.core.navigation.NavGraphProvider
import com.spaceex.feature.home.contract.HomeScreenDestination
import com.spaceex.feature.home.presentation.ui.HomeScreenRoute

internal class HomeProvider : NavGraphProvider {
    override fun registerGraph(provider: NavGraphBuilder) {
        provider.apply {
            composable<HomeScreenDestination> {
                HomeScreenRoute()
            }
        }
    }
}