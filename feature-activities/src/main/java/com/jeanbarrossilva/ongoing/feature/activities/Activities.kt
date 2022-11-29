package com.jeanbarrossilva.ongoing.feature.activities

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.effect.ResumedFetchEffect
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycards.ActivityCards
import com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.Scaffold
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.plus
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.collectAsState
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toSerializableList
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun Activities(
    navigator: DestinationsNavigator,
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

    ResumedFetchEffect(fetcher)

    Activities(
        user,
        activities,
        onAccountDetailsRequest = {
            user?.let { boundary.navigateToAccount(navigator, it) }
                ?: boundary.navigateToAuthentication(navigator)
        },
        onActivityDetailsRequest = {
            boundary.navigateToActivityDetails(
                context,
                viewModel.session,
                activityRegistry,
                observation,
                fetcher,
                navigator,
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
        onAccountDetailsRequest = { },
        onActivityDetailsRequest = { },
        onAddRequest = { },
        modifier
    )
}

@Composable
private fun Activities(
    user: User?,
    activities: Loadable<SerializableList<ContextualActivity>>,
    onAccountDetailsRequest: () -> Unit,
    onActivityDetailsRequest: (activity: ContextualActivity) -> Unit,
    onAddRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { TopAppBar(user, onAccountDetailsRequest) },
        modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = onAddRequest) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription =
                        stringResource(R.string.feature_activities_fab_content_description)
                )
            }
        }
    ) {
        Background {
            ActivityCards(
                activities,
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