package com.spaceex.core.cache

sealed interface CacheResult<out T> {
    data class Success<T>(val data: T) : CacheResult<T>
    data class Error(val error: Throwable) : CacheResult<Nothing>
}