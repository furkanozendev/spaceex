package com.spaceex.core.network.status

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    val status: Flow<NetworkStatus>
}