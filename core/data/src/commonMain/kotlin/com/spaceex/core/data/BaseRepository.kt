package com.spaceex.core.data

import com.spaceex.core.cache.CacheResult
import com.spaceex.core.domain.RestResult
import com.spaceex.core.network.helper.asRestResult
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

abstract class BaseRepository {

    protected suspend inline fun <reified T : Any> safeApiCall(
        crossinline call: suspend () -> HttpResponse
    ): RestResult<T> = withContext(Dispatchers.IO) {
        try {
            call().asRestResult()
        } catch (e: Exception) {
            RestResult.Error<T>(e)
        }
    }

    protected suspend inline fun <reified T> safeCacheCall(
        crossinline call: suspend () -> T
    ): CacheResult<T> = withContext(Dispatchers.IO) {
        try {
            CacheResult.Success(call())
        } catch (e: Exception) {
            CacheResult.Error(e)
        }
    }

    protected inline fun <reified NetworkType : Any, reified LocalType, DomainType> offlineFirstFlow(
        crossinline fetchFromNetwork: suspend () -> HttpResponse,
        crossinline saveToLocal: suspend (NetworkType) -> Unit,
        crossinline readFromLocal: suspend () -> LocalType,
        crossinline mapToDomain: (LocalType) -> DomainType,
        crossinline mapNetworkToDomain: (NetworkType) -> DomainType,
        crossinline shouldFetch: (LocalType) -> Boolean = { true }
    ): Flow<RestResult<DomainType>> = flow {
        emit(RestResult.Loading<Nothing>())

        val cacheResponse = safeCacheCall<LocalType> { readFromLocal() }
        val cachedData = (cacheResponse as? CacheResult.Success)?.data

        val hasCachedData = cachedData != null && (cachedData !is List<*> || cachedData.isNotEmpty())

        if (hasCachedData) {
            emit(RestResult.Loading(result = mapToDomain(cachedData as LocalType)))
        }

        if (cachedData != null && !shouldFetch(cachedData)) {
            emit(RestResult.Success(mapToDomain(cachedData)))
            return@flow
        }

        val networkResponse = safeApiCall<NetworkType> { fetchFromNetwork() }

        when (networkResponse) {
            is RestResult.Success -> {
                safeCacheCall<Unit> { saveToLocal(networkResponse.result) }

                val freshCacheResponse = safeCacheCall<LocalType> { readFromLocal() }
                val freshData = (freshCacheResponse as? CacheResult.Success)?.data

                if (freshData != null && (freshData !is List<*> || (freshData as List<*>).isNotEmpty())) {
                    emit(RestResult.Success(mapToDomain(freshData)))
                } else {
                    emit(RestResult.Success(mapNetworkToDomain(networkResponse.result)))
                }
            }

            is RestResult.Loading -> {
                emit(RestResult.Loading<Nothing>())
            }

            is RestResult.Error -> {
                val currentData = if (hasCachedData) mapToDomain(cachedData as LocalType) else null

                val throwable = networkResponse.error
                emit(RestResult.Error(throwable, result = currentData))
            }
        }
    }

    protected inline fun <reified NetworkType : Any, DomainType> networkOnlyFlow(
        crossinline fetchFromNetwork: suspend () -> HttpResponse,
        crossinline mapToDomain: (NetworkType) -> DomainType
    ): Flow<RestResult<DomainType>> = flow {
        emit(RestResult.Loading<Nothing>())

        val networkResponse = safeApiCall<NetworkType> { fetchFromNetwork() }

        when (networkResponse) {
            is RestResult.Success -> {
                emit(RestResult.Success(mapToDomain(networkResponse.result)))
            }

            is RestResult.Error -> {
                emit(RestResult.Error(networkResponse.error))
            }

            is RestResult.Loading -> {
                emit(RestResult.Loading<Nothing>())
            }
        }
    }
}
