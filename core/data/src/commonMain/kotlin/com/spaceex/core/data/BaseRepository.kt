package com.spaceex.core.data

import com.spaceex.core.cache.CacheHandler
import com.spaceex.core.cache.CacheResult
import com.spaceex.core.domain.RestResult
import com.spaceex.core.network.handler.RequestHandler
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseRepository(
    @PublishedApi internal val requestHandler: RequestHandler,
    @PublishedApi internal val cacheHandler: CacheHandler
) {

    protected inline fun <reified NetworkType : Any, reified LocalType, DomainType> offlineFirstFlow(
        crossinline fetchFromNetwork: suspend () -> HttpResponse,
        crossinline saveToLocal: suspend (NetworkType) -> Unit,
        crossinline readFromLocal: suspend () -> LocalType,
        crossinline mapToDomain: (LocalType) -> DomainType,
        crossinline shouldFetch: (LocalType) -> Boolean = { true }
    ): Flow<RestResult<DomainType>> = flow {
        emit(RestResult.Loading<Nothing>())

        val cacheResponse = cacheHandler.query<LocalType> { readFromLocal() }
        val cachedData = (cacheResponse as? CacheResult.Success)?.data

        val hasCachedData = cachedData != null && (cachedData !is List<*> || cachedData.isNotEmpty())

        if (hasCachedData) {
            emit(RestResult.Loading(result = mapToDomain(cachedData as LocalType)))
        }

        if (cachedData != null && !shouldFetch(cachedData)) {
            emit(RestResult.Success(mapToDomain(cachedData)))
            return@flow
        }

        val networkResponse = requestHandler.request<NetworkType> { fetchFromNetwork() }

        when (networkResponse) {
            is RestResult.Success -> {
                cacheHandler.query<Unit> { saveToLocal(networkResponse.result) }

                val freshCacheResponse = cacheHandler.query<LocalType> { readFromLocal() }
                val freshData = (freshCacheResponse as? CacheResult.Success)?.data

                if (freshData != null) {
                    emit(RestResult.Success(mapToDomain(freshData)))
                } else {
                    emit(RestResult.Error<Nothing>(Exception("Cache read failed after network save.")))
                }
            }

            is RestResult.Loading<*> -> {
                emit(RestResult.Loading<Nothing>())
            }

            is RestResult.Error<*> -> {
                val currentData = if (hasCachedData) mapToDomain(cachedData as LocalType) else null

                val throwable = networkResponse.error
                emit(RestResult.Error<DomainType?>(throwable, result = currentData))
            }
        }
    }

    protected suspend inline fun <reified T : Any> networkOnly(
        crossinline call: suspend () -> HttpResponse
    ): RestResult<T> {
        return requestHandler.request(call)
    }
}
