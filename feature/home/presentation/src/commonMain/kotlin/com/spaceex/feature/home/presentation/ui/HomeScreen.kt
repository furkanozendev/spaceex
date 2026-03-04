package com.spaceex.feature.home.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spaceex.core.designsystem.component.SpaceexAsyncImage
import com.spaceex.core.designsystem.theme.SpaceexTheme
import com.spaceex.feature.home.domain.model.Launch
import com.spaceex.feature.home.domain.model.LaunchStatus
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreenRoute(
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(uiState = uiState, actions = viewModel)
}

@Composable
internal fun HomeScreen(uiState: HomeUiState, actions: HomeActions) {
    AnimatedContent(
        targetState = uiState.isLoading,
        transitionSpec = {
            fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
        },
        label = "HomeScreenState"
    ) { isLoading ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LaunchListScreen(launches = uiState.launch, actions = actions)
        }
    }
}

@Composable
internal fun LaunchListScreen(launches: List<Launch>, actions: HomeActions) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                text = "SpaceX Launches",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        items(
            items = launches,
            key = { it.id }
        ) { launch ->
            LaunchItem(
                launch = launch,
                modifier = Modifier.animateItem(),
                onClick = { actions.navigateToDetail(launch.id) }
            )
        }
    }
}

@Composable
fun LaunchItem(
    launch: Launch,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LaunchImage(imageUrl = launch.imageUrl, missionName = launch.missionName)
            LaunchContent(launch = launch, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun LaunchImage(imageUrl: String?, missionName: String) {
    SpaceexAsyncImage(
        image = imageUrl.orEmpty(),
        contentDescription = "$missionName patch",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
private fun LaunchContent(launch: Launch, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        LaunchHeader(missionName = launch.missionName, flightNumber = launch.flightNumber)
        Spacer(modifier = Modifier.height(4.dp))
        LaunchSubHeader(status = launch.status, dateFormatted = launch.dateFormatted)
        Spacer(modifier = Modifier.height(8.dp))
        LaunchDescription(description = launch.description)
        LaunchFooter(youtubeId = launch.youtubeId, hasLandingSuccess = launch.hasLandingSuccess)
    }
}

@Composable
private fun LaunchHeader(missionName: String, flightNumber: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = missionName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "#$flightNumber",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun LaunchSubHeader(status: LaunchStatus, dateFormatted: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatusBadge(status = status)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = dateFormatted,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun LaunchDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun LaunchFooter(youtubeId: String?, hasLandingSuccess: Boolean) {
    if (youtubeId != null || hasLandingSuccess) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (youtubeId != null) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Red
                )
            }
            if (hasLandingSuccess) {
                Text(
                    text = "🛬 Landing Success",
                    style = MaterialTheme.typography.labelSmall,
                    color = SpaceexTheme.extendedColors.success
                )
            }
        }
    }
}

@Composable
fun StatusBadge(status: LaunchStatus) {
    val backgroundColor: Color
    val textColor: Color

    when (status) {
        LaunchStatus.SUCCESS -> {
            backgroundColor = SpaceexTheme.extendedColors.successContainer
            textColor = SpaceexTheme.extendedColors.success
        }

        LaunchStatus.FAILED -> {
            backgroundColor = SpaceexTheme.extendedColors.errorContainer
            textColor = SpaceexTheme.extendedColors.error
        }

        LaunchStatus.UPCOMING -> {
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