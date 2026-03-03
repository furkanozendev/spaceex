package com.spaceex.core.network.helper

/*
inline fun <T, R> RestResult<T>.mapOnSuccess(map: (T?) -> R): RestResult<R> = when (this) {
    is RestResult.Success -> RestResult.Success(map(result))
    is RestResult.Error -> this
    is RestResult.Loading -> this
}
*/
