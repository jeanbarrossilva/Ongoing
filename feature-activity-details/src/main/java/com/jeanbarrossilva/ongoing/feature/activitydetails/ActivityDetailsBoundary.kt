package com.jeanbarrossilva.ongoing.feature.activitydetails

import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

interface ActivityDetailsBoundary {
    fun navigateToActivityEditing(navigator: DestinationsNavigator, activity: ContextualActivity?)
}