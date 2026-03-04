package com.spaceex.feature.home.data.mapper

import com.spaceex.feature.home.contract.model.LaunchContractModel
import com.spaceex.feature.home.contract.model.LaunchContractStatus
import com.spaceex.feature.home.data.cache.entity.LaunchEntity
import com.spaceex.feature.home.data.network.dto.LaunchDto
import com.spaceex.feature.home.domain.model.Launch
import com.spaceex.feature.home.domain.model.LaunchStatus

fun LaunchDto.toDomain(): Launch {
    return Launch(
        id = id,
        missionName = name,
        flightNumber = flightNumber,
        dateFormatted = dateUtc.substringBefore("T"),
        status = when (success) {
            true -> LaunchStatus.SUCCESS
            false -> LaunchStatus.FAILED
            else -> LaunchStatus.UPCOMING
        },
        description = details ?: "No details provided.",
        imageUrl = links.patch?.small ?: links.patch?.large,
        hasLandingSuccess = cores.any { it.landingSuccess == true },
        rocketId = rocketId
    )
}

fun LaunchDto.toEntity(): LaunchEntity {
    return LaunchEntity(
        id = id,
        name = name,
        flightNumber = flightNumber,
        dateUtc = dateUtc,
        success = success,
        details = details,
        imageUrl = links.patch?.small,
        webcast = links.webcast,
        article = links.wikipedia,
        rocketId = rocketId
    )
}

fun LaunchEntity.toDomain(): Launch {
    return Launch(
        id = id,
        missionName = name,
        flightNumber = flightNumber,
        dateFormatted = dateUtc.substringBefore("T"),
        status = when (success) {
            true -> LaunchStatus.SUCCESS
            false -> LaunchStatus.FAILED
            else -> LaunchStatus.UPCOMING
        },
        description = details ?: "",
        imageUrl = imageUrl,
        hasLandingSuccess = false,
        rocketId = rocketId
    )
}

fun LaunchEntity.toContractModel(): LaunchContractModel {
    return LaunchContractModel(
        id = id,
        missionName = name,
        flightNumber = flightNumber,
        dateFormatted = dateUtc.substringBefore("T"),
        status = when (success) {
            true -> LaunchContractStatus.SUCCESS
            false -> LaunchContractStatus.FAILED
            else -> LaunchContractStatus.UPCOMING
        },
        description = details ?: "",
        imageUrl = imageUrl,
        webcast = webcast,
        article = article,
        hasLandingSuccess = false,
        rocketId = rocketId
    )
}
