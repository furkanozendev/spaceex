package com.spaceex.feature.detail.presentation.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spaceex.core.domain.RestResult
import com.spaceex.core.navigation.NavigationCommand
import com.spaceex.core.navigation.NavigationManager
import com.spaceex.core.presentation.CoreViewModel
import com.spaceex.feature.detail.contract.DetailScreenDestination
import com.spaceex.feature.detail.domain.usecase.GetRocketDetailUseCase
import com.spaceex.feature.home.contract.provider.LaunchProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import spaceex.feature.detail.presentation.generated.resources.Res
import spaceex.feature.detail.presentation.generated.resources.launch_data_not_found_text
import spaceex.feature.detail.presentation.generated.resources.rocket_data_not_found_text

internal class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getRocketDetail: GetRocketDetailUseCase,
    private val launchProvider: LaunchProvider,
    private val navigationManager: NavigationManager
) : CoreViewModel(), DetailActions {

    private val args = savedStateHandle.toRoute<DetailScreenDestination>()

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val rocketStateFlow = MutableStateFlow<RocketUiState>(RocketUiState.Loading)

    init {
        loadDetailData()
    }

    private fun loadDetailData() {
        _uiState.value = DetailUiState.Loading
        fetchRocketData(args.rocketId)

        viewModelScope.launch {
            launchProvider.getLaunchById(args.launchId)
                .combine(rocketStateFlow) { localLaunch, rocketState ->
                    if (localLaunch == null) {
                        DetailUiState.Error(getString(Res.string.launch_data_not_found_text))
                    } else {
                        DetailUiState.Success(
                            launch = localLaunch,
                            rocketState = rocketState
                        )
                    }
                }
                .collect { combinedState ->
                    _uiState.value = combinedState
                }
        }
    }

    private fun fetchRocketData(rocketId: String) {
        viewModelScope.launch {
            rocketStateFlow.value = RocketUiState.Loading
            safeFlowApiCall { getRocketDetail.invoke(rocketId) }
                .collect { result ->
                    when (result) {
                        is RestResult.Success -> {
                            rocketStateFlow.value = RocketUiState.Success(result.result)
                        }

                        is RestResult.Error -> {
                            val errorMessage = getString(Res.string.rocket_data_not_found_text)
                            rocketStateFlow.value = RocketUiState.Error(errorMessage)
                        }

                        is RestResult.Loading -> {
                            rocketStateFlow.value = RocketUiState.Loading
                        }
                    }
                }
        }
    }

    override fun onBackClick() {
        navigationManager.navigate(NavigationCommand.NavigateUp)
    }

    override fun onRetryRocket(rocketId: String) {
        fetchRocketData(rocketId)
    }

    override fun onRetryLaunch() {
        loadDetailData()
    }
}
