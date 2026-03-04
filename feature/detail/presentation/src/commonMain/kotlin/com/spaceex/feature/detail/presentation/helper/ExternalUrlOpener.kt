package com.spaceex.feature.detail.presentation.helper

import androidx.compose.runtime.Composable

@Composable
expect fun rememberExternalUrlOpener(): (String) -> Unit