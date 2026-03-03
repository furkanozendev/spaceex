package com.spaceex.core.cache

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

open class CacheHandler {
    suspend inline fun <reified T> query(
        crossinline call: suspend () -> T
    ): CacheResult<T> = withContext(Dispatchers.IO) {
        try {
            CacheResult.Success(call.invoke())
        } catch (e: Exception) {
            CacheResult.Error(error = e)
        }
    }
}