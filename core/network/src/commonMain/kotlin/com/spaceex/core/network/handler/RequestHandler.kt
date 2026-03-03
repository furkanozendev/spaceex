package com.spaceex.core.network.handler

import com.spaceex.core.domain.RestResult
import com.spaceex.core.network.helper.asRestResult
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

open class RequestHandler {
    suspend inline fun <reified T : Any> request(
        crossinline call: suspend () -> HttpResponse
    ): RestResult<T> = withContext(context = Dispatchers.IO) {
        try {
            call.invoke().asRestResult()
        } catch (e: Exception) {
            RestResult.Error<Nothing>(error = e)
        }
    }
}