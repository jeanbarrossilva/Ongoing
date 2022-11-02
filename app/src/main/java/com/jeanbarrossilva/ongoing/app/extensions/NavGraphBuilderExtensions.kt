package com.jeanbarrossilva.ongoing.app.extensions

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activities.Activities
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesViewModel
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetails
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsViewModel

internal fun NavGraphBuilder.activities(
    userRepository: UserRepository,
    activityRegistry: ActivityRegistry,
    navController: NavController,
    boundary: ActivitiesBoundary
) {
    composable(Activities.ROUTE) {
        val viewModelFactory = ActivitiesViewModel.createFactory(userRepository, activityRegistry)
        val viewModel = viewModel<ActivitiesViewModel>(factory = viewModelFactory)
        Activities(navController, viewModel, boundary)
    }
}

internal fun NavGraphBuilder.activityDetails(
    navController: NavController,
    userRepository: UserRepository,
    activityRegistry: ActivityRegistry,
    boundary: ActivityDetailsBoundary
) {
    composable(ActivityDetails.ROUTE) {
        val activityId = it.arguments?.getString(ActivityDetails.ARGUMENT_NAME)
        val viewModelFactory =
            ActivityDetailsViewModel.createFactory(userRepository, activityRegistry, activityId)
        val viewModel = viewModel<ActivityDetailsViewModel>(factory = viewModelFactory)
        ActivityDetails(boundary, viewModel, onNavigationRequest = navController::popBackStack)
    }
}