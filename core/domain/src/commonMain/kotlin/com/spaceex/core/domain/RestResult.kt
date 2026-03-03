package com.spaceex.core.domain

sealed class RestResult<out T> {

    data class Success<out T>(
        val result: T
    ) : RestResult<T>()

    data class Error<out T>(
        val error: Throwable,
        val result: T? = null
    ) : RestResult<Nothing>()

    data class Loading<out T>(val result: T? = null) : RestResult<Nothing>()
}
