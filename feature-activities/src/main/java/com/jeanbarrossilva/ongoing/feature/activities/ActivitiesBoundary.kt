package com.jeanbarrossilva.ongoing.feature.activities

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.user.User

interface ActivitiesBoundary {
    fun navigateToAuthentication(context: Context)

    fun navigateToSettings(context: Context, user: User)

    fun navigateToActivityDetails(
        context: Context,
        sessionManager: SessionManager,
        activityRegistry: ActivityRegistry,
        observation: Observation,
        fetcher: ContextualActivitiesFetcher,
        activityId: String
    )

    fun navigateToActivityEditing(context: Context)

    companion object {
        internal val empty = object: ActivitiesBoundary {
            override fun navigateToAuthentication(context: Context) {
            }

            override fun navigateToSettings(context: Context, user: User) {
            }

            override fun navigateToActivityDetails(
                context: Context,
                sessionManager: SessionManager,
                activityRegistry: ActivityRegistry,
                observation: Observation,
                fetcher: ContextualActivitiesFetcher,
                activityId: String
            ) {
            }

            override fun navigateToActivityEditing(context: Context) {
            }
        }
    }
}