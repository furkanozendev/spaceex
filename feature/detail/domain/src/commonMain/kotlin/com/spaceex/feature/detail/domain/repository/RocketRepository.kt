package com.spaceex.feature.detail.domain.repository

import com.spaceex.core.domain.RestResult
import com.spaceex.feature.detail.domain.model.Rocket
import kotlinx.coroutines.flow.Flow

interface RocketRepository {
    fun getRocketDetail(id: String): Flow<RestResult<Rocket>>
}