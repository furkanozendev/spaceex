package com.spaceex.feature.home.presentation.ui

import androidx.lifecycle.viewModelScope
import com.spaceex.core.domain.RestResult
import com.spaceex.core.presentation.CoreViewModel
import com.spaceex.feature.home.domain.usecase.GetLaunchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getLaunches: GetLaunchesUseCase
) : CoreViewModel(), HomeActions {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState>
        get() = _uiState

    init {
        getLaunches()
    }

    fun getLaunches() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            safeFlowApiCall { getLaunches.invoke() }
                .collect { result ->
                    when (result) {
                        is RestResult.Success -> {
                            _uiState.value = _uiState.value.copy(isLoading = false, launch = result.result)
                        }

                        is RestResult.Error -> {
                            result.error.printStackTrace()
                            val errorMessage = result.error.message ?: "Unknown error"
                            _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                        }

                        is RestResult.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true, launch = result.result.orEmpty())
                        }
                    }
                }
        }
    }

    override fun navigateToDetail(rocketId: String) {}
}