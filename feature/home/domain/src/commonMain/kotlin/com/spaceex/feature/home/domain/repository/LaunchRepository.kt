package com.spaceex.feature.home.domain.repository

import com.spaceex.core.domain.RestResult
import com.spaceex.feature.home.domain.model.Launch
import kotlinx.coroutines.flow.Flow

interface LaunchRepository {
    fun getLaunches(): Flow<RestResult<List<Launch>>>
}