package com.spaceex.core.network.status

import kotlinx.coroutines.flow.Flow

expect class NetworkConnectivityObserver : ConnectivityObserver {
    override val status: Flow<NetworkStatus>
}