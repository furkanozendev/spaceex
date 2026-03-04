package com.spaceex.feature.home.contract.provider

import com.spaceex.feature.home.contract.model.LaunchContractModel
import kotlinx.coroutines.flow.Flow

interface LaunchProvider {
    fun getLaunchById(id: String): Flow<LaunchContractModel?>
}
