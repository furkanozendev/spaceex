package com.spaceex.core.network

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

actual fun getHttpEngineFactory(): HttpClientEngineFactory<*> = OkHttp