package com.spaceex.feature.detail.domain.model

data class Rocket(
    val id: String,
    val name: String,
    val description: String,
    val heightMeters: Double,
    val massKg: Double,
    val firstFlightDate: String,
    val isActive: Boolean,
    val mainImageUrl: String?,
    val engineInfo: String,
    val successRateFactor: Float
)
