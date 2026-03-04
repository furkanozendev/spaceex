package com.spaceex.feature.detail.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EngineDto(
    @SerialName("type") val type: String,
    @SerialName("version") val version: String,
    @SerialName("propellant_1") val propellant1: String,
    @SerialName("propellant_2") val propellant2: String
)
