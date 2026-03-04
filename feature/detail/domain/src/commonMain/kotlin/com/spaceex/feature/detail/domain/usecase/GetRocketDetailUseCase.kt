package com.spaceex.feature.detail.domain.usecase

import com.spaceex.core.domain.RestResult
import com.spaceex.feature.detail.domain.model.Rocket
import com.spaceex.feature.detail.domain.repository.RocketRepository
import kotlinx.coroutines.flow.Flow

class GetRocketDetailUseCase(
    private val repository: RocketRepository
) {
    operator fun invoke(id: String): Flow<RestResult<Rocket>> {
        return repository.getRocketDetail(id)
    }
}