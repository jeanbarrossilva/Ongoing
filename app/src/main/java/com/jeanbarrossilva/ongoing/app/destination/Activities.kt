package com.jeanbarrossilva.ongoing.app.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.feature.activities.Activities
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesViewModel
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.feature.authentication.prompter.AuthenticationPrompter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.get

@Composable
@Destination(start = true)
internal fun Activities(navigator: DestinationsNavigator, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val session = get<Session>()
    val activityRegistry = get<ActivityRegistry>()
    val observation = get<Observation>()
    val authenticationPrompter = get<AuthenticationPrompter>()
    val boundary = get<ActivitiesBoundary>()
    val fetcher = get<ContextualActivitiesFetcher>()
    val viewModelFactory = remember {
        ActivitiesViewModel.createFactory(session, fetcher)
    }
    val viewModel = viewModel<ActivitiesViewModel>(factory = viewModelFactory)

    LaunchedEffect(Unit) {
        authenticationPrompter.prompt(context) {
            boundary.navigateToAuthentication(context)
        }
    }

    Activities(navigator, viewModel, boundary, activityRegistry, observation, modifier)
}