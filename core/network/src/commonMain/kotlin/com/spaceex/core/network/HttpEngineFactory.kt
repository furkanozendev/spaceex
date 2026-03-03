package com.spaceex.core.network

import io.ktor.client.engine.HttpClientEngineFactory

expect fun getHttpEngineFactory(): HttpClientEngineFactory<*>