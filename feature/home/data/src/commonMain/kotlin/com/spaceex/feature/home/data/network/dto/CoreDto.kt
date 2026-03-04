package com.spaceex.feature.home.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoreDto(
    @SerialName("core") val coreId: String? = null,
    @SerialName("flight") val flight: Int? = null,
    @SerialName("landing_success") val landingSuccess: Boolean? = null
)
