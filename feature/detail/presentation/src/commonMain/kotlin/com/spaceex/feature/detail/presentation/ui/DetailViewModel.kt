package com.spaceex.feature.detail.presentation.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spaceex.core.domain.RestResult
import com.spaceex.core.presentation.CoreViewModel
import com.spaceex.feature.detail.contract.DetailScreenDestination
import com.spaceex.feature.detail.domain.usecase.GetRocketDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class DetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getRocketDetail: GetRocketDetailUseCase
) : CoreViewModel(), DetailActions {

    val args by lazy {
        savedStateHandle.toRoute<DetailScreenDestination>()
    }

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState>
        get() = _uiState

    init {
        getRocketDetail(args.rocketId)
    }

    private fun getRocketDetail(rocketId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            safeFlowApiCall { getRocketDetail.invoke(rocketId) }
                .collect { result ->
                    when (result) {
                        is RestResult.Success -> {
                            _uiState.value = _uiState.value.copy(isLoading = false, rocket = result.result)
                        }

                        is RestResult.Error -> {
                            result.error.printStackTrace()
                            val errorMessage = result.error.message ?: "Unknown error"
                            _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = errorMessage)
                        }

                        is RestResult.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true, rocket = result.result)
                        }
                    }
                }
        }
    }

    override fun onClickYoutube() {
        TODO("Not yet implemented")
    }

    override fun onClickMoreDetail() {
        TODO("Not yet implemented")
    }
}