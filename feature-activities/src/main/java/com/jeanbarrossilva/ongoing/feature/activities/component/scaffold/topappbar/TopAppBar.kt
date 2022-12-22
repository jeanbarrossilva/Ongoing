package com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.topappbar

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.core.user.User
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesSelection
import com.jeanbarrossilva.ongoing.feature.activities.R
import com.jeanbarrossilva.ongoing.feature.activities.extensions.ifOn
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarStyle
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun TopAppBar(
    user: User?,
    selection: ActivitiesSelection,
    onSettingsRequest: () -> Unit,
    onUnregistrationRequest: (activities: List<ContextualActivity>) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectionTitle = selection.ifOn {
        context.resources.getQuantityString(
            R.plurals.feature_activities_selection,
            selected.size,
            selected.size
        )
    }
    val defaultTile = stringResource(R.string.feature_activities)

    TopAppBar(TopAppBarStyle.Root, modifier, title = { Text(selectionTitle ?: defaultTile) }) {
        when (selection) {
            is ActivitiesSelection.On ->
                TopAppBarSelectionActions(selection, onUnregistrationRequest)
            is ActivitiesSelection.Off -> TopAppBarDefaultActions(user, onSettingsRequest)
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TopAppBarPreview() {
    OngoingTheme {
        TopAppBar(
            user = null,
            ActivitiesSelection.Off,
            onSettingsRequest = { },
            onUnregistrationRequest = { }
        )
    }
}