package com.spaceex.feature.home.data.provider

import com.spaceex.feature.home.contract.model.LaunchContractModel
import com.spaceex.feature.home.contract.provider.LaunchProvider
import com.spaceex.feature.home.data.cache.LaunchDao
import com.spaceex.feature.home.data.mapper.toContractModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class LaunchProviderImpl(
    private val dao: LaunchDao
) : LaunchProvider {
    override fun getLaunchById(id: String): Flow<LaunchContractModel?> {
        return dao.getLaunchById(id).map { it?.toContractModel() }
    }
}