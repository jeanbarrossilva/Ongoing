package com.jeanbarrossilva.ongoing.feature.activities

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.ActivityCard
import com.jeanbarrossilva.ongoing.platform.designsystem.component.Scaffold
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarRelevance
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun Activities(
    navigator: DestinationsNavigator,
    viewModel: ActivitiesViewModel,
    boundary: ActivitiesBoundary,
    modifier: Modifier = Modifier
) {
    val activities by viewModel.activities.collectAsState(initial = emptyList())

    Activities(
        activities,
        onActivityDetailsNavigationRequest = {
            boundary.navigateToActivityDetails(navigator, it.id)
        },
        onAddRequest = { boundary.navigateToActivityEditing(navigator) },
        modifier
    )
}

@Composable
private fun Activities(
    activities: List<ContextualActivity>,
    onActivityDetailsNavigationRequest: (ContextualActivity) -> Unit,
    onAddRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(TopAppBarRelevance.Main) {
                Text(stringResource(R.string.feature_activities))
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddRequest) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription =
                        stringResource(R.string.feature_activities_fab_content_description)
                )
            }
        }
    ) { padding ->
        Background(
            modifier
                .padding(Size.Spacing.xxxl)
                .padding(padding)
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(Size.Spacing.s)) {
                items(activities) {
                    ActivityCard(
                        it,
                        onClick = { onActivityDetailsNavigationRequest(it) },
                        Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivitiesPreview() {
    OngoingTheme {
        Activities(
            ContextualActivity.samples,
            onActivityDetailsNavigationRequest = { },
            onAddRequest = { }
        )
    }
}