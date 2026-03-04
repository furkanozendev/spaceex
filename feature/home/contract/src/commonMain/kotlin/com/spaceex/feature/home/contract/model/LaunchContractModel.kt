package com.spaceex.feature.home.contract.model

import androidx.compose.runtime.Immutable

@Immutable
data class LaunchContractModel(
    val id: String,
    val missionName: String,
    val flightNumber: Int,
    val dateFormatted: String,
    val status: LaunchContractStatus,
    val description: String,
    val imageUrl: String?,
    val webcast: String?,
    val article: String?,
    val hasLandingSuccess: Boolean,
    val rocketId: String?
)