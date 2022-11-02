package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.ActivityHeadline
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.ActivityStatusHistory
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.designsystem.component.Scaffold
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarRelevance
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

object ActivityDetails {
    const val ROUTE = "activity/{activityId}"
    const val ARGUMENT_NAME = "activityId"

    fun route(activityId: String): String {
        return ROUTE.replace("{$ARGUMENT_NAME}", activityId)
    }
}

@Composable
fun ActivityDetails(
    boundary: ActivityDetailsBoundary,
    viewModel: ActivityDetailsViewModel,
    onNavigationRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity by viewModel.activity.collectAsState(null)

    ActivityDetails(
        activity,
        onNavigationRequest,
        onEditRequest = { boundary.navigateToActivityEditing(context, activity) },
        modifier
    )
}

@Composable
private fun ActivityDetails(
    activity: ContextualActivity?,
    onNavigationRequest: () -> Unit,
    onEditRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { TopAppBar(TopAppBarRelevance.Subsequent(onNavigationRequest)) },
        floatingActionButton = {
            FloatingActionButton(
                isAvailable = activity != null,
                onClick = onEditRequest
            )
        },
        modifier
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
        ActivityDetails(ContextualActivity.sample, onNavigationRequest = { }, onEditRequest = { })
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadingActivityDetailsPreview() {
    OngoingTheme {
        ActivityDetails(activity = null, onNavigationRequest = { }, onEditRequest = { })
    }
}