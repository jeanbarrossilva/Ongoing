package com.jeanbarrossilva.ongoing.app.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeanbarrossilva.ongoing.app.destination.destinations.AuthenticationDestination
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activities.Activities
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesViewModel
import com.jeanbarrossilva.ongoing.feature.authentication.prompter.AuthenticationPrompter
import com.jeanbarrossilva.ongoing.platform.extensions.onFirstRun
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.get

@Composable
@Destination(start = true)
internal fun Activities(navigator: DestinationsNavigator, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val userRepository = get<UserRepository>()
    val activityRegistry = get<ActivityRegistry>()
    val authenticationPrompter = get<AuthenticationPrompter>()
    val boundary = get<ActivitiesBoundary>()
    val viewModelFactory =
        remember { ActivitiesViewModel.createFactory(userRepository, activityRegistry) }
    val viewModel = viewModel<ActivitiesViewModel>(factory = viewModelFactory)

    LaunchedEffect(Unit) {
        context.onFirstRun {
            authenticationPrompter.prompt {
                navigator.navigate(AuthenticationDestination)
            }
        }
    }

    Activities(navigator, viewModel, boundary, modifier)
}