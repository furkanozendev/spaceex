package com.spaceex.feature.home.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinksDto(
    @SerialName("patch") val patch: PatchDto? = null,
    @SerialName("webcast") val webcast: String? = null,
    @SerialName("wikipedia") val wikipedia: String? = null,
    @SerialName("youtube_id") val youtubeId: String? = null
)
