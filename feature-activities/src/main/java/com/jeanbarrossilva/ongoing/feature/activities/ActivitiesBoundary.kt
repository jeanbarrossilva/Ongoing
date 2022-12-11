package com.jeanbarrossilva.ongoing.feature.activities

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

interface ActivitiesBoundary {
    fun navigateToAuthentication(context: Context)

    fun navigateToAccount(context: Context, user: User)

    fun navigateToActivityDetails(
        context: Context,
        session: Session,
        activityRegistry: ActivityRegistry,
        observation: Observation,
        fetcher: ContextualActivitiesFetcher,
        navigator: DestinationsNavigator,
        activityId: String
    )

    fun navigateToActivityEditing(context: Context)
}