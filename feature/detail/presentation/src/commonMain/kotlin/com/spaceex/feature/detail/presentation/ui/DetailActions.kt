package com.spaceex.feature.detail.presentation.ui

internal interface DetailActions {
    fun onBackClick()
    fun onRetryRocket(rocketId: String)
    fun onRetryLaunch()
}
