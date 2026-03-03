package com.spaceex.feature.home.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("flight_number") val flightNumber: Int,
    @SerialName("date_utc") val dateUtc: String,
    @SerialName("success") val success: Boolean? = null,
    @SerialName("details") val details: String? = null,
    @SerialName("links") val links: LinksDto,
    @SerialName("failures") val failures: List<FailureDto> = emptyList(),
    @SerialName("cores") val cores: List<CoreDto> = emptyList()
)