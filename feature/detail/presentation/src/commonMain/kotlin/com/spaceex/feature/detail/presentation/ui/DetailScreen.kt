package com.spaceex.feature.detail.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spaceex.core.designsystem.component.SpaceexAsyncImage
import com.spaceex.core.designsystem.screen.ErrorScreen
import com.spaceex.core.designsystem.theme.SpaceexTheme
import com.spaceex.feature.detail.domain.model.Rocket
import com.spaceex.feature.detail.presentation.helper.rememberExternalUrlOpener
import com.spaceex.feature.home.contract.model.LaunchContractModel
import com.spaceex.feature.home.contract.model.LaunchContractStatus
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailScreenRoute(
    viewModel: DetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailScreen(uiState = uiState, actions = viewModel)
}

@Composable
internal fun DetailScreen(uiState: DetailUiState, actions: DetailActions) {
    AnimatedContent(
        modifier = Modifier.fillMaxSize(),
        targetState = uiState,
        transitionSpec = {
            fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
        },
        label = "DetailScreenState"
    ) { state ->
        when (state) {
            is DetailUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is DetailUiState.Error -> {
                ErrorScreen(message = state.message, onRetry = actions::onRetryLaunch)
            }

            is DetailUiState.Success -> {
                CombinedDetailContent(
                    launch = state.launch,
                    rocketState = state.rocketState,
                    actions = actions
                )
            }
        }
    }
}

@Composable
private fun CombinedDetailContent(
    launch: LaunchContractModel,
    rocketState: RocketUiState,
    actions: DetailActions
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 48.dp)
    ) {
        LaunchHeroSection(launch = launch, onBackClick = actions::onBackClick)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            LaunchInfoSection(launch = launch)
            LaunchActionsSection(launch = launch)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
            )

            Text(
                text = "Launch Vehicle",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            when (rocketState) {
                is RocketUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is RocketUiState.Error -> {
                    RocketErrorCard(message = rocketState.message) {
                        launch.rocketId?.let { actions.onRetryRocket(it) }
                    }
                }

                is RocketUiState.Success -> {
                    RocketDetailContent(rocket = rocketState.rocket)
                }
            }
        }
    }
}

@Composable
private fun LaunchHeroSection(launch: LaunchContractModel, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(start = 16.dp, top = 8.dp)
                .size(44.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(12.dp)
                )
                .zIndex(2f)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Navigate Back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }


        if (launch.imageUrl != null) {
            SpaceexAsyncImage(
                image = launch.imageUrl.orEmpty(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background.copy(alpha = 0.6f),
                            MaterialTheme.colorScheme.background
                        ),
                        startY = 100f
                    )
                )
        )
    }
}

@Composable
private fun LaunchInfoSection(launch: LaunchContractModel) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = launch.missionName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )
            StatusBadge(status = launch.status)
        }

        Text(
            text = "Flight #${launch.flightNumber} • ${launch.dateFormatted}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = launch.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2f
        )
    }
}

@Composable
private fun LaunchActionsSection(launch: LaunchContractModel) {
    val openExternalUrl = rememberExternalUrlOpener()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (launch.webcast != null) {
            Button(
                onClick = {
                    openExternalUrl(launch.webcast.orEmpty())
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Webcast")
            }
        }

        if (launch.article != null) {
            OutlinedButton(
                onClick = {
                    openExternalUrl(launch.article.orEmpty())
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onBackground)
            ) {
                Icon(imageVector = Icons.Default.Info, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Details")
            }
        }
    }
}

@Composable
private fun RocketDetailContent(rocket: Rocket) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        RocketTitleRow(name = rocket.name, isActive = rocket.isActive)

        if (rocket.mainImageUrl != null) {
            SpaceexAsyncImage(
                image = rocket.mainImageUrl.orEmpty(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        RocketStatsRow(
            heightMeters = rocket.heightMeters,
            massKg = rocket.massKg,
            firstFlightDate = rocket.firstFlightDate
        )
        RocketSection(title = "Specs", content = rocket.description)
        RocketSection(title = "Engines", content = rocket.engineInfo)
    }
}

@Composable
private fun RocketErrorCard(message: String, onRetry: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "Rocket Data Unavailable",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text("Retry Connection")
            }
        }
    }
}

@Composable
private fun RocketTitleRow(name: String, isActive: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
        ActiveStatusBadge(isActive = isActive)
    }
}

@Composable
private fun ActiveStatusBadge(isActive: Boolean) {
    val backgroundColor =
        if (isActive) SpaceexTheme.extendedColors.successContainer else SpaceexTheme.extendedColors.errorContainer
    val textColor = if (isActive) SpaceexTheme.extendedColors.success else SpaceexTheme.extendedColors.error
    val text = if (isActive) "ACTIVE" else "INACTIVE"

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Composable
private fun RocketStatsRow(heightMeters: Double, massKg: Double, firstFlightDate: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RocketStatCard(
            modifier = Modifier.weight(0.8f),
            label = "Height",
            value = "${heightMeters}m"
        )
        RocketStatCard(
            modifier = Modifier.weight(0.8f),
            label = "Mass",
            value = "${massKg / 1000}t"
        )
        RocketStatCard(
            modifier = Modifier.weight(1.4f),
            label = "First Flight",
            value = firstFlightDate
        )
    }
}

@Composable
private fun RocketStatCard(modifier: Modifier = Modifier, label: String, value: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun RocketSection(title: String, content: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2f
        )
    }
}

@Composable
fun StatusBadge(status: LaunchContractStatus) {
    val backgroundColor: Color
    val textColor: Color

    when (status) {
        LaunchContractStatus.SUCCESS -> {
            backgroundColor = SpaceexTheme.extendedColors.successContainer
            textColor = SpaceexTheme.extendedColors.success
        }

        LaunchContractStatus.FAILED -> {
            backgroundColor = SpaceexTheme.extendedColors.errorContainer
            textColor = SpaceexTheme.extendedColors.error
        }

        LaunchContractStatus.UPCOMING -> {
            backgroundColor = SpaceexTheme.extendedColors.upcomingContainer
            textColor = SpaceexTheme.extendedColors.upcoming
        }
    }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}