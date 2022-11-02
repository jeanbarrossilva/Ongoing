package com.jeanbarrossilva.ongoing.app.extensions

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activities.Activities
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesViewModel


internal fun NavGraphBuilder.activities(
    userRepository: UserRepository,
    activityRegistry: ActivityRegistry,
    boundary: ActivitiesBoundary
) {
    composable(Activities.ROUTE) {
        val viewModelFactory = ActivitiesViewModel.createFactory(userRepository, activityRegistry)
        val viewModel = viewModel<ActivitiesViewModel>(factory = viewModelFactory)
        Activities(viewModel, boundary)
    }
}