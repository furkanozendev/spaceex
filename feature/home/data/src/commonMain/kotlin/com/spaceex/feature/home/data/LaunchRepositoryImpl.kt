package com.spaceex.feature.home.data

import com.spaceex.core.data.BaseRepository
import com.spaceex.core.domain.RestResult
import com.spaceex.feature.home.data.cache.LaunchDao
import com.spaceex.feature.home.data.cache.entity.LaunchEntity
import com.spaceex.feature.home.data.mapper.toDomain
import com.spaceex.feature.home.data.mapper.toEntity
import com.spaceex.feature.home.data.network.LaunchApi
import com.spaceex.feature.home.data.network.dto.LaunchDto
import com.spaceex.feature.home.domain.model.Launch
import com.spaceex.feature.home.domain.repository.LaunchRepository
import kotlinx.coroutines.flow.Flow

class LaunchRepositoryImpl(
    private val api: LaunchApi,
    private val dao: LaunchDao
) : LaunchRepository, BaseRepository() {

    override fun getLaunches(): Flow<RestResult<List<Launch>>> {
        return offlineFirstFlow<List<LaunchDto>, List<LaunchEntity>, List<Launch>>(
            fetchFromNetwork = { api.getLaunches() },
            saveToLocal = { networkDtos ->
                dao.insertLaunches(networkDtos.map { it.toEntity() })
            },
            readFromLocal = { dao.getAllLaunches() },
            mapNetworkToDomain = { dto ->
                dto.map { it.toDomain() }
            },
            mapToDomain = { localEntities ->
                localEntities.map { it.toDomain() }
            }
        )
    }
}
