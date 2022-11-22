package com.jeanbarrossilva.ongoing.app.boundary

import com.jeanbarrossilva.ongoing.app.destination.destinations.ActivityEditingDestination
import com.jeanbarrossilva.ongoing.app.extensions.toActivityEditingMode
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

internal class DefaultActivityDetailsBoundary: ActivityDetailsBoundary {
    override fun navigateToActivityEditing(
        activity: ActivityDetailsActivity,
        navigator: DestinationsNavigator,
        contextualActivity: ContextualActivity?
    ) {
        val mode = contextualActivity.toActivityEditingMode()
        val destination = ActivityEditingDestination(mode)
        navigator.navigate(destination)
    }
}