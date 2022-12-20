package com.jeanbarrossilva.ongoing.feature.activities

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.effect.ResumedFetchEffect
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.activities.component.RemovalConfirmationDialog
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycards.ActivityCards
import com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.Scaffold
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.plus
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.collectAsState
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toSerializableList
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList

@Composable
fun Activities(
    viewModel: ActivitiesViewModel,
    boundary: ActivitiesBoundary,
    activityRegistry: ActivityRegistry,
    observation: Observation,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val fetcher = viewModel.fetcher
    val user by viewModel.user.collectAsState(initial = null)
    val activities by viewModel.activities.collectAsState()
    val selection by viewModel.selection.collectAsState()

    ResumedFetchEffect(fetcher)

    Activities(
        user,
        activities,
        selection,
        onSelectionChange = { viewModel.selection.value = it },
        onSettingsRequest = {
            user?.let { boundary.navigateToSettings(context, it) }
                ?: boundary.navigateToAuthentication(context)
        },
        onUnregistrationRequest = viewModel::unregister,
        onActivityDetailsRequest = {
            boundary.navigateToActivityDetails(
                context,
                viewModel.session,
                activityRegistry,
                observation,
                fetcher,
                it.id
            )
        },
        onAddRequest = { boundary.navigateToActivityEditing(context) },
        modifier
    )
}

@Composable
private fun Activities(
    activities: Loadable<SerializableList<ContextualActivity>>,
    modifier: Modifier = Modifier
) {
    Activities(
        User.sample,
        activities,
        ActivitiesSelection.Off,
        onSelectionChange = { },
        onSettingsRequest = { },
        onUnregistrationRequest = { },
        onActivityDetailsRequest = { },
        onAddRequest = { },
        modifier
    )
}

@Composable
private fun Activities(
    user: User?,
    activities: Loadable<SerializableList<ContextualActivity>>,
    selection: ActivitiesSelection,
    onSelectionChange: (selection: ActivitiesSelection) -> Unit,
    onSettingsRequest: () -> Unit,
    onUnregistrationRequest: (activities: List<ContextualActivity>) -> Unit,
    onActivityDetailsRequest: (activity: ContextualActivity) -> Unit,
    onAddRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isRemovalConfirmationDialogVisible by remember { mutableStateOf(false) }

    if (isRemovalConfirmationDialogVisible && selection is ActivitiesSelection.On) {
        RemovalConfirmationDialog(
            selection,
            onDismissalRequest = { isRemovalConfirmationDialogVisible = false },
            onConfirmationRequest = { onUnregistrationRequest(selection.selected) }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                user,
                selection,
                onSettingsRequest,
                onUnregistrationRequest = { isRemovalConfirmationDialogVisible = true }
            )
        },
        modifier,
        floatingActionButton = { FloatingActionButton(onClick = onAddRequest) }
    ) {
        Background {
            ActivityCards(
                activities,
                selection,
                onSelectionChange,
                contentPadding = PaddingValues(Size.Spacing.xxxl) + it,
                onActivityDetailsRequest
            )
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SuccessfulActivitiesPreview() {
    OngoingTheme {
        Activities(Loadable.Loaded(ContextualActivity.samples.toSerializableList()))
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadingActivitiesPreview() {
    OngoingTheme {
        Activities(Loadable.Loading())
    }
}