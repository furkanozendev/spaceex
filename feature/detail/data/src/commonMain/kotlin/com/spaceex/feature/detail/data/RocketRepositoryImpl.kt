package com.spaceex.feature.detail.data

import com.spaceex.core.data.BaseRepository
import com.spaceex.core.domain.RestResult
import com.spaceex.feature.detail.data.mapper.toDomain
import com.spaceex.feature.detail.data.network.RocketApi
import com.spaceex.feature.detail.data.network.dto.RocketDto
import com.spaceex.feature.detail.domain.model.Rocket
import com.spaceex.feature.detail.domain.repository.RocketRepository
import kotlinx.coroutines.flow.Flow

class RocketRepositoryImpl(
    private val api: RocketApi
) : RocketRepository, BaseRepository() {

    override fun getRocketDetail(rocketId: String): Flow<RestResult<Rocket>> {
        return networkOnlyFlow<RocketDto, Rocket>(
            fetchFromNetwork = {
                api.getRocketDetail(rocketId)
            },
            mapToDomain = { dto ->
                dto.toDomain()
            }
        )
    }
}
