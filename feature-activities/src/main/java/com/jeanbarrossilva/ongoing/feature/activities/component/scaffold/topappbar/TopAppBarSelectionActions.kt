package com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.topappbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesSelection
import com.jeanbarrossilva.ongoing.feature.activities.R

internal const val TOP_APP_BAR_SELECTION_ACTIONS_REMOVE_TAG = "top_app_bar_selection_actions_remove"

@Suppress("UnusedReceiverParameter")
@Composable
internal fun RowScope.TopAppBarSelectionActions(
    selection: ActivitiesSelection.On,
    onUnregistrationRequest: (activities: List<ContextualActivity>) -> Unit
) {
    IconButton(
        onClick = { onUnregistrationRequest(selection.selected) },
        Modifier.testTag(TOP_APP_BAR_SELECTION_ACTIONS_REMOVE_TAG)
    ) {
        Icon(
            Icons.Rounded.Delete,
            contentDescription = stringResource(
                R.string.feature_activities_top_app_bar_action_remove_content_description
            )
        )
    }
}