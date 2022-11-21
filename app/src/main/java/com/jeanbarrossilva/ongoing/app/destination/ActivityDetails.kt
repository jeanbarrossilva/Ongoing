package com.jeanbarrossilva.ongoing.app.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetails
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.get

@Composable
@Destination
internal fun ActivityDetails(
    navigator: DestinationsNavigator,
    activityId: String,
    modifier: Modifier = Modifier
) {
    val session = get<Session>()
    val userRepository = get<UserRepository>()
    val activityRegistry = get<ActivityRegistry>()
    val boundary = get<ActivityDetailsBoundary>()
    val viewModelFactory = remember {
        ActivityDetailsViewModel.createFactory(
            session,
            userRepository,
            activityRegistry,
            activityId
        )
    }
    val viewModel = viewModel<ActivityDetailsViewModel>(factory = viewModelFactory)
    ActivityDetails(navigator, boundary, viewModel, modifier)
}