package com.spaceex.core.data

import com.spaceex.core.domain.RestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
): Flow<RestResult<ResultType>> = flow {

    val data = query().first()
    emit(RestResult.Loading(data))

    try {
        if (shouldFetch(data)) {
            val fetchResult = fetch()
            saveFetchResult(fetchResult)
        }

        emitAll(query().map { RestResult.Success(it) })
    } catch (throwable: Throwable) {
        emitAll(query().map { RestResult.Error(throwable, it) })
    }
}