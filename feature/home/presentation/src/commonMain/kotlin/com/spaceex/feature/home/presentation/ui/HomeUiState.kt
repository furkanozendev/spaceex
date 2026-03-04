package com.spaceex.feature.home.presentation.ui

import com.spaceex.feature.home.domain.model.Launch

internal data class HomeUiState(
    val launch: List<Launch> = emptyList(), // TODO Add Immutable list
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)