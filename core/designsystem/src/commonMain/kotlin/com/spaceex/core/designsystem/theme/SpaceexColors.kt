package com.spaceex.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LightSuccess = Color(0xFF2E7D32)
val LightSuccessContainer = Color(0xFFE8F5E9)
val LightError = Color(0xFFC62828)
val LightErrorContainer = Color(0xFFFFEBEE)
val LightUpcoming = Color(0xFF1565C0)
val LightUpcomingContainer = Color(0xFFE3F2FD)

val DarkSuccess = Color(0xFF81C784)
val DarkSuccessContainer = Color(0xFF1B5E20)
val DarkError = Color(0xFFE57373)
val DarkErrorContainer = Color(0xFFB71C1C)
val DarkUpcoming = Color(0xFF64B5F6)
val DarkUpcomingContainer = Color(0xFF0D47A1)

@Immutable
data class SpaceexExtendedColors(
    val success: Color,
    val successContainer: Color,
    val error: Color,
    val errorContainer: Color,
    val upcoming: Color,
    val upcomingContainer: Color
)

val LocalSpaceXExtendedColors = staticCompositionLocalOf {
    SpaceexExtendedColors(
        success = Color.Unspecified,
        successContainer = Color.Unspecified,
        error = Color.Unspecified,
        errorContainer = Color.Unspecified,
        upcoming = Color.Unspecified,
        upcomingContainer = Color.Unspecified
    )
}

val LightExtendedColors = SpaceexExtendedColors(
    success = LightSuccess,
    successContainer = LightSuccessContainer,
    error = LightError,
    errorContainer = LightErrorContainer,
    upcoming = LightUpcoming,
    upcomingContainer = LightUpcomingContainer
)

val DarkExtendedColors = SpaceexExtendedColors(
    success = DarkSuccess,
    successContainer = DarkSuccessContainer,
    error = DarkError,
    errorContainer = DarkErrorContainer,
    upcoming = DarkUpcoming,
    upcomingContainer = DarkUpcomingContainer
)