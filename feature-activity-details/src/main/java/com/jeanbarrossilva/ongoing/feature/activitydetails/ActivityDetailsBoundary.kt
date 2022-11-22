package com.jeanbarrossilva.ongoing.feature.activitydetails

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

interface ActivityDetailsBoundary {
    fun navigateToActivityEditing(
        activity: ActivityDetailsActivity,
        navigator: DestinationsNavigator,
        contextualActivity: ContextualActivity?
    )
}