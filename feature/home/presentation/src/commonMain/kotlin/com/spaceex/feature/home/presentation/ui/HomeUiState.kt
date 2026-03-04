package com.spaceex.feature.home.presentation.ui

import androidx.compose.runtime.Immutable
import com.spaceex.feature.home.domain.model.Launch

@Immutable
internal sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val launches: List<Launch>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}