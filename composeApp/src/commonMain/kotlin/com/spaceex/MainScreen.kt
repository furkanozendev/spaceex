package com.spaceex

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.spaceex.core.navigation.NavGraphProvider
import com.spaceex.core.navigation.NavigationManager
import com.spaceex.feature.home.contract.HomeScreenDestination
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun MainScreen() {
    val navController = rememberNavController()

    val providers: List<NavGraphProvider> = getKoin().getAll()

    val navigationManager = getKoin().get<NavigationManager>()

    CompositionLocalProvider {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { paddingValues ->
                NavHost(
                    modifier = Modifier.padding(paddingValues),
                    navController = navController,
                    startDestination = HomeScreenDestination
                ) {
                    providers.forEach {
                        it.registerGraph(provider = this)
                    }
                }
            }
        )
    }

    NavigationLaunchedEffect(
        navigationManager = navigationManager,
        navController = navController
    )
}
