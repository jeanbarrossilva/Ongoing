package com.jeanbarrossilva.ongoing.app.boundary

import android.content.Context
import com.jeanbarrossilva.ongoing.app.destination.destinations.AccountDestination
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.bridge.ActivityDetailsBridge
import com.jeanbarrossilva.ongoing.feature.activitydetails.bridge.ActivityDetailsBridgeCrossing
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingMode
import com.jeanbarrossilva.ongoing.feature.authentication.AuthenticationActivity
import com.jeanbarrossilva.ongoing.platform.extensions.startActivity
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

internal class DefaultActivitiesBoundary(
    private val activityDetailsBoundary: ActivityDetailsBoundary
): ActivitiesBoundary {
    override fun navigateToAuthentication(context: Context) {
        context.startActivity<AuthenticationActivity>()
    }

    override fun navigateToAccount(navigator: DestinationsNavigator, user: User) {
        val destination = AccountDestination(user)
        navigator.navigate(destination)
    }

    override fun navigateToActivityDetails(
        context: Context,
        session: Session,
        activityRegistry: ActivityRegistry,
        observation: Observation,
        fetcher: ContextualActivitiesFetcher,
        navigator: DestinationsNavigator,
        activityId: String
    ) {
        ActivityDetailsBridge.cross(
            context,
            session,
            activityRegistry,
            observation,
            fetcher,
            activityDetailsBoundary,
            ActivityDetailsBridgeCrossing.Start(activityId)
        )
    }

    override fun navigateToActivityEditing(context: Context) {
        ActivityEditingActivity.start(context, ActivityEditingMode.Addition)
    }
}