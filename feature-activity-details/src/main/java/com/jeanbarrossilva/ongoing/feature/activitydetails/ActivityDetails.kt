package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.ActivityHeadline
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.ActivityStatusHistory
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarRelevance
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun ActivityDetails(
    boundary: ActivityDetailsBoundary,
    activity: ContextualActivity,
    onNavigationRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    ActivityDetails(
        activity,
        onNavigationRequest,
        onEditRequest = { boundary.navigateToActivityEditing(context, activity) },
        modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActivityDetails(
    activity: ContextualActivity,
    onNavigationRequest: () -> Unit,
    onEditRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier,
        topBar = { TopAppBar(TopAppBarRelevance.Subsequent(onNavigationRequest)) },
        floatingActionButton = {
            FloatingActionButton(onClick = onEditRequest) {
                Icon(Icons.Rounded.Edit, contentDescription = "Edit")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
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
private fun ActivityDetailsPreview() {
    OngoingTheme {
        ActivityDetails(ContextualActivity.sample, onNavigationRequest = { }, onEditRequest = { })
    }
}