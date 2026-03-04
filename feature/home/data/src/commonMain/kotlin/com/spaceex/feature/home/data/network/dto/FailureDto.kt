package com.spaceex.feature.home.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FailureDto(
    @SerialName("time") val time: Int? = null,
    @SerialName("reason") val reason: String? = null
)
