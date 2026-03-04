package com.spaceex.feature.detail.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class RocketApi(
    private val httpClient: HttpClient
) {

    suspend fun getRocketDetail(rocketId: String) =
        httpClient.request {
            url(urlString = "https://api.spacexdata.com/v4/rockets/$rocketId")

            method = HttpMethod.Get
        }
}
