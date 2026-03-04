package com.spaceex.feature.home.domain.model

data class Launch(
    val id: String,
    val missionName: String,
    val flightNumber: Int,
    val dateFormatted: String,
    val status: LaunchStatus,
    val description: String,
    val imageUrl: String?,
    val hasLandingSuccess: Boolean,
    val rocketId: String?
)
