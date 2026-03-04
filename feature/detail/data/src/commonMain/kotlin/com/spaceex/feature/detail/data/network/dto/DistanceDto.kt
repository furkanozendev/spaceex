package com.spaceex.feature.detail.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DistanceDto(
    @SerialName("meters") val meters: Double? = null,
    @SerialName("feet") val feet: Double? = null
)