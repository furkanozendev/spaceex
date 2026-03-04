package com.spaceex.feature.home.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchDto(
    @SerialName("small") val small: String? = null,
    @SerialName("large") val large: String? = null
)
