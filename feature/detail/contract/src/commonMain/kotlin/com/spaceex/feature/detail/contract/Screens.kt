@file:Suppress("Filename", "MatchingDeclarationName")

package com.spaceex.feature.detail.contract

import com.spaceex.core.navigation.NavigationCommand
import kotlinx.serialization.Serializable

@Serializable
data class DetailScreenDestination(
    val rocketId: String,
    val launchId: String
) : NavigationCommand.Destination
