package com.jeanbarrossilva.ongoing.app.boundary

import com.jeanbarrossilva.ongoing.app.destination.destinations.ActivityDetailsDestination
import com.jeanbarrossilva.ongoing.app.destination.destinations.ActivityEditingDestination
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingMode
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

internal class DefaultActivitiesBoundary: ActivitiesBoundary {
    override fun navigateToActivityDetails(navigator: DestinationsNavigator, activityId: String) {
        val destination = ActivityDetailsDestination(activityId)
        navigator.navigate(destination)
    }

    override fun navigateToActivityEditing(navigator: DestinationsNavigator) {
        val destination = ActivityEditingDestination(ActivityEditingMode.Addition)
        navigator.navigate(destination)
    }
}