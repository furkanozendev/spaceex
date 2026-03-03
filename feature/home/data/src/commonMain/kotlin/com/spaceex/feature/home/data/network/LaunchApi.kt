package com.spaceex.feature.home.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class LaunchApi(
    private val httpClient: HttpClient
) {

    suspend fun getLaunches() =
        httpClient.request {
            url(urlString = "https://api.spacexdata.com/v4/launches")

            method = HttpMethod.Get
        }
}