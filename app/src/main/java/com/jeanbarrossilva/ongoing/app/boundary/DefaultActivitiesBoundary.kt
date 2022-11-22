package com.jeanbarrossilva.ongoing.app.boundary

import android.content.Context
import com.jeanbarrossilva.ongoing.app.destination.destinations.AccountDestination
import com.jeanbarrossilva.ongoing.app.destination.destinations.AuthenticationDestination
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingMode
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

internal class DefaultActivitiesBoundary: ActivitiesBoundary {
    override fun navigateToAuthentication(navigator: DestinationsNavigator) {
        navigator.navigate(AuthenticationDestination)
    }

    override fun navigateToAccount(navigator: DestinationsNavigator, user: User) {
        val destination = AccountDestination(user)
        navigator.navigate(destination)
    }

    override fun navigateToActivityDetails(
        context: Context,
        navigator: DestinationsNavigator,
        activityId: String
    ) {
        ActivityDetailsActivity.start(context, activityId)
    }

    override fun navigateToActivityEditing(context: Context) {
        ActivityEditingActivity.start(context, ActivityEditingMode.Addition)
    }
}