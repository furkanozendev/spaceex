package com.spaceex.feature.detail.presentation.ui

import com.spaceex.feature.detail.domain.model.Rocket

internal data class DetailUiState(
    val rocket: Rocket? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)