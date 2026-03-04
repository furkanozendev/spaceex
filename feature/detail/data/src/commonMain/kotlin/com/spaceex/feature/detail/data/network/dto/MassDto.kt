package com.spaceex.feature.detail.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MassDto(
    @SerialName("kg") val kg: Double? = null,
    @SerialName("lb") val lb: Double? = null
)