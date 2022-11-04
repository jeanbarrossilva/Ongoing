package com.jeanbarrossilva.ongoing.feature.activities

import com.ramcosta.composedestinations.navigation.DestinationsNavigator

interface ActivitiesBoundary {
    fun navigateToActivityDetails(navigator: DestinationsNavigator, activityId: String)

    fun navigateToActivityEditing(navigator: DestinationsNavigator)
}