package com.spaceex.feature.home.presentation.ui

import androidx.lifecycle.viewModelScope
import com.spaceex.core.domain.RestResult
import com.spaceex.core.navigation.NavigationManager
import com.spaceex.core.presentation.CoreViewModel
import com.spaceex.feature.detail.contract.DetailScreenDestination
import com.spaceex.feature.home.domain.usecase.GetLaunchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getLaunches: GetLaunchesUseCase,
    private val navigationManager: NavigationManager
) : CoreViewModel(), HomeActions {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadLaunches()
    }

    private fun loadLaunches() {
        viewModelScope.launch {
            safeFlowApiCall { getLaunches.invoke() }
                .collect { result ->
                    when (result) {
                        is RestResult.Success -> {
                            _uiState.value = HomeUiState.Success(result.result)
                        }

                        is RestResult.Error -> {
                            val data = result.result
                            if (data.isNullOrEmpty()) {
                                val errorMessage = result.error.message ?: "Unknown error occurred"
                                _uiState.value = HomeUiState.Error(errorMessage)
                            } else {
                                _uiState.value = HomeUiState.Success(data)
                            }
                        }

                        is RestResult.Loading -> {
                            val data = result.result
                            if (data.isNullOrEmpty()) {
                                _uiState.value = HomeUiState.Loading
                            } else {
                                _uiState.value = HomeUiState.Success(data)
                            }
                        }
                    }
                }
        }
    }

    override fun navigateToDetail(rocketId: String?, launchId: String) {
        if (rocketId == null) return
        navigationManager.navigate(
            navigationCommand = DetailScreenDestination(
                rocketId = rocketId,
                launchId = launchId
            )
        )
    }

    override fun retry() {
        _uiState.value = HomeUiState.Loading
        loadLaunches()
    }
}
