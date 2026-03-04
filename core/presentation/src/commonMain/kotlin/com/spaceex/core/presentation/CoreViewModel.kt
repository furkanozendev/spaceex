package com.spaceex.core.presentation

import androidx.lifecycle.ViewModel
import com.spaceex.core.domain.RestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class CoreViewModel : ViewModel() {

    fun <T : Any> safeFlowApiCall(
        call: () -> Flow<RestResult<T>>
    ): Flow<RestResult<T>> {
        return call().catch { throwable ->
            emit(RestResult.Error<T>(throwable))
        }
    }
}
