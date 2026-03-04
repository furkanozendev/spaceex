package com.spaceex.feature.detail.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.spaceex.core.navigation.NavGraphProvider
import com.spaceex.feature.detail.contract.DetailScreenDestination
import com.spaceex.feature.detail.presentation.ui.DetailScreenRoute

internal class DetailProvider : NavGraphProvider {
    override fun registerGraph(provider: NavGraphBuilder) {
        provider.apply {
            composable<DetailScreenDestination> {
                DetailScreenRoute()
            }
        }
    }
}