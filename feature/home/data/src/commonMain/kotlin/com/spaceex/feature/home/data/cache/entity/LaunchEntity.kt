package com.spaceex.feature.home.data.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launches")
data class LaunchEntity(
    @PrimaryKey val id: String,
    val name: String,
    val flightNumber: Int,
    val dateUtc: String,
    val success: Boolean?,
    val details: String?,
    val imageUrl: String?,
    val webcast: String?,
    val article: String?,
    val rocketId: String?
)
