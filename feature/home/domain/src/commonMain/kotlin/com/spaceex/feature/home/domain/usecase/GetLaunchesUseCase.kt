package com.spaceex.feature.home.domain.usecase

import com.spaceex.core.domain.RestResult
import com.spaceex.feature.home.domain.model.Launch
import com.spaceex.feature.home.domain.repository.LaunchRepository
import kotlinx.coroutines.flow.Flow

class GetLaunchesUseCase(
    private val repository: LaunchRepository
) {
    operator fun invoke(): Flow<RestResult<List<Launch>>> {
        return repository.getLaunches()
    }
}
