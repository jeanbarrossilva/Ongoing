package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.ActivityHeadline
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.ActivityStatusHistory
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.Scaffold
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.collectAsState
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.ifLoaded
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.map

@Composable
fun ActivityDetails(
    boundary: ActivityDetailsBoundary,
    viewModel: ActivityDetailsViewModel,
    onNavigationRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val contextualActivity by viewModel.activity.collectAsState()

    ActivityDetails(
        contextualActivity,
        onObservationToggle = {
            viewModel.setObserving(it) {
                ActivityDetailsToaster.onObservationToggle(context, it)
            }
        },
        onNavigationRequest,
        onEditRequest = {
            contextualActivity.ifLoaded {
                boundary.navigateToActivityEditing(context, this)
            }
        },
        modifier
    )
}

@Composable
private fun ActivityDetails(
    activity: Loadable<ContextualActivity>,
    modifier: Modifier = Modifier
) {
    ActivityDetails(
        activity,
        onObservationToggle = { },
        onNavigationRequest = { },
        onEditRequest = { },
        modifier
    )
}

@Composable
private fun ActivityDetails(
    activity: Loadable<ContextualActivity>,
    onObservationToggle: (isObserving: Boolean) -> Unit,
    onNavigationRequest: () -> Unit,
    onEditRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                activity.map(ContextualActivity::isObserving),
                onObservationToggle,
                onNavigationRequest
            )
        },
        modifier,
        floatingActionButton = {
            FloatingActionButton(
                isAvailable = activity is Loadable.Loaded,
                onClick = onEditRequest
            )
        }
    ) {
        Background(
            Modifier
                .padding(Size.Spacing.xxxl)
                .padding(it)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(Size.Spacing.xxxxxl)) {
                ActivityHeadline(activity, Modifier.fillMaxWidth())
                ActivityStatusHistory(activity)
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadedActivityDetailsPreview() {
    OngoingTheme {
        ActivityDetails(Loadable.Loaded(ContextualActivity.sample))
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadingActivityDetailsPreview() {
    OngoingTheme {
        ActivityDetails(Loadable.Loading())
    }
}