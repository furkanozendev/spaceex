package com.spaceex.core.network.helper

import com.spaceex.core.domain.RestResult
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

val StatusException = Exception(message = "Status Exception")

suspend inline fun <reified T> HttpResponse.asRestResult(): RestResult<T> {
    return if (!status.isSuccess()) {
        RestResult.Error<Nothing>(error = StatusException)
    } else {
        val body = this.body<T>() ?: return RestResult.Error<Nothing>(error = StatusException)
        RestResult.Success(body)
    }
}
