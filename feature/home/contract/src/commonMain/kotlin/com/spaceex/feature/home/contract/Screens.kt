@file:Suppress("Filename", "MatchingDeclarationName")

package com.spaceex.feature.home.contract

import com.spaceex.core.navigation.NavigationCommand
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenDestination : NavigationCommand.Destination
