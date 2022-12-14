package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.ActivityHeadline
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.ActivityStatusHistory
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold.TopAppBar
import com.jeanbarrossilva.ongoing.feature.activitydetails.domain.ContextActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.createSample
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
    onObservationToggle: (isObserving: Boolean) -> Unit,
    onNavigationRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val activity by viewModel.activity.collectAsState()

    ActivityDetails(
        activity,
        onObservationToggle,
        onNavigationRequest,
        onEditRequest = {
            activity.ifLoaded {
                boundary.navigateToActivityEditing(coroutineScope, context, id)
            }
        },
        modifier
    )
}

@Composable
private fun ActivityDetails(
    activity: Loadable<ContextActivity>,
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
    activity: Loadable<ContextActivity>,
    onObservationToggle: (isObserving: Boolean) -> Unit,
    onNavigationRequest: () -> Unit,
    onEditRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                activity.map(ContextActivity::isBeingObserved),
                onObservationToggle,
                onNavigationRequest
            )
        },
        modifier,
        floatingActionButton = {
            FloatingActionButton(
                isAvailable = activity.ifLoaded(ContextActivity::isEditable) ?: false,
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
private fun LoadingActivityDetailsPreview() {
    OngoingTheme {
        ActivityDetails(Loadable.Loading())
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadedActivityDetailsPreview() {
    OngoingTheme {
        ActivityDetails(Loadable.Loaded(ContextActivity.createSample(LocalContext.current)))
    }
}
