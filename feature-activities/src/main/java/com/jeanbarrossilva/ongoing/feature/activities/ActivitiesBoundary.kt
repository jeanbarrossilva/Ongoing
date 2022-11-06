package com.jeanbarrossilva.ongoing.feature.activities

import com.jeanbarrossilva.ongoing.core.session.user.User
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

interface ActivitiesBoundary {
    fun navigateToAuthentication(navigator: DestinationsNavigator)

    fun navigateToAccount(navigator: DestinationsNavigator, user: User)

    fun navigateToActivityDetails(navigator: DestinationsNavigator, activityId: String)

    fun navigateToActivityEditing(navigator: DestinationsNavigator)
}