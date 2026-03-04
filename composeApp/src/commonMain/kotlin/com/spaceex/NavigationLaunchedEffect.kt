package com.spaceex

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.spaceex.core.navigation.NavigationCommand
import com.spaceex.core.navigation.NavigationManager

@Composable
internal fun NavigationLaunchedEffect(
    navigationManager: NavigationManager,
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        navigationManager.navigationCommandFlow.collect { command ->
            when (command) {
                NavigationCommand.NavigateUp -> {
                    navController.navigateUp()
                }

                is NavigationCommand.PopBackStackTo -> {
                    navController.popBackStack(route = command.to, inclusive = command.inclusive)
                }

                is NavigationCommand.NavigateTo -> {
                    if (command.clearBackStack) {
                        navController.popBackStack()
                    }

                    navController.navigate(route = command.to) {
                        if (!command.addToBackStack) {
                            popUpTo(route = command.to)
                        }
                    }
                }

                is NavigationCommand.Destination -> {
                    navController.navigate(route = command)
                }
            }
        }
    }
}
