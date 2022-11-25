package com.jeanbarrossilva.ongoing.feature.activitydetails.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.feature.activitydetails.R
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.BackgroundContentSizing
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.PlaceholderSize
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.placeholder
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.ifLoaded

@Composable
internal fun ActivityObservationIcon(
    isObserving: Loadable<Boolean>,
    modifier: Modifier = Modifier
) {
    val size = 24.dp
    val vector = remember(isObserving) {
        isObserving.ifLoaded {
            if (this) Icons.Rounded.Notifications else Icons.Outlined.Notifications
        }
    }

    Box(
        modifier
            .placeholder(PlaceholderSize of size, isVisible = isObserving is Loadable.Loading)
            .size(size)
    ) {
        vector?.let {
            Icon(
                it,
                contentDescription = stringResource(R.string.feature_activity_details_notify)
            )
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadingActivityObservationIconPreview() {
    OngoingTheme {
        ActivityObservationIcon(isObserving = Loadable.Loading())
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadedNonObservingActivityObservationIconPreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            ActivityObservationIcon(isObserving = Loadable.Loaded(false))
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadedObservingActivityObservingIconPreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            ActivityObservationIcon(isObserving = Loadable.Loaded(true))
        }
    }
}