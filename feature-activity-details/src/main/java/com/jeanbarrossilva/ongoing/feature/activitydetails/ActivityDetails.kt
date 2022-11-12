package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.ActivityHeadline
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.ActivityStatusHistory
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.designsystem.component.Scaffold
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarStyle
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.collectAsState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ActivityDetails(
    navigator: DestinationsNavigator,
    boundary: ActivityDetailsBoundary,
    viewModel: ActivityDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val activity by viewModel.activity.collectAsState()

    ActivityDetails(
        activity,
        onNavigationRequest = navigator::popBackStack,
        onEditRequest = {
            activity.ifLoaded {
                boundary.navigateToActivityEditing(navigator, this)
            }
        },
        modifier
    )
}

@Composable
private fun ActivityDetails(
    activity: Loadable<ContextualActivity>,
    onNavigationRequest: () -> Unit,
    onEditRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { TopAppBar(TopAppBarStyle.Navigable(onNavigationRequest)) },
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
        ActivityDetails(
            Loadable.Loaded(ContextualActivity.sample),
            onNavigationRequest = { },
            onEditRequest = { }
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadingActivityDetailsPreview() {
    OngoingTheme {
        ActivityDetails(
            Loadable.Loading(),
            onNavigationRequest = { },
            onEditRequest = { }
        )
    }
}