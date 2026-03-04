package com.spaceex.feature.detail.presentation.ui

import androidx.compose.runtime.Immutable
import com.spaceex.feature.detail.domain.model.Rocket
import com.spaceex.feature.home.contract.model.LaunchContractModel

@Immutable
internal sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Error(val message: String) : DetailUiState
    data class Success(
        val launch: LaunchContractModel,
        val rocketState: RocketUiState
    ) : DetailUiState
}

@Immutable
internal sealed interface RocketUiState {
    data object Loading : RocketUiState
    data class Success(val rocket: Rocket) : RocketUiState
    data class Error(val message: String) : RocketUiState
}
