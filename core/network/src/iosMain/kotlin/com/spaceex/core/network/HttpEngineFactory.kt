package com.spaceex.core.network

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

actual fun getHttpEngineFactory(): HttpClientEngineFactory<*> = Darwin