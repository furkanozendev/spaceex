package com.spaceex.feature.detail.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("active") val active: Boolean,
    @SerialName("stages") val stages: Int,
    @SerialName("cost_per_launch") val costPerLaunch: Long,
    @SerialName("success_rate_pct") val successRate: Int,
    @SerialName("first_flight") val firstFlight: String,
    @SerialName("description") val description: String,
    @SerialName("height") val height: DistanceDto,
    @SerialName("diameter") val diameter: DistanceDto,
    @SerialName("mass") val mass: MassDto,
    @SerialName("engines") val engines: EngineDto,
    @SerialName("flickr_images") val images: List<String>
)
