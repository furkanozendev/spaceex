package com.spaceex.feature.detail.data.mapper

import com.spaceex.feature.detail.data.network.dto.RocketDto
import com.spaceex.feature.detail.domain.model.Rocket

fun RocketDto.toDomain(): Rocket {
    return Rocket(
        id = id,
        name = name,
        description = description,
        heightMeters = height.meters ?: 0.0,
        massKg = mass.kg ?: 0.0,
        firstFlightDate = firstFlight,
        isActive = active,
        mainImageUrl = images.firstOrNull(),
        engineInfo = "${engines.type.uppercase()} ${engines.version}",
        successRateFactor = successRate / 100f
    )
}