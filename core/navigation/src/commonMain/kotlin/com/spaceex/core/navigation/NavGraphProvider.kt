package com.spaceex.core.navigation

import androidx.navigation.NavGraphBuilder

fun interface NavGraphProvider {
    fun registerGraph(provider: NavGraphBuilder)
}
