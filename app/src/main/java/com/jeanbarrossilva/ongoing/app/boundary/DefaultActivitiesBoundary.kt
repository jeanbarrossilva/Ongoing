package com.jeanbarrossilva.ongoing.app.boundary

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.user.ContextualUser
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.bridge.ActivityDetailsBridge
import com.jeanbarrossilva.ongoing.feature.activitydetails.bridge.ActivityDetailsBridgeCrossing
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingMode
import com.jeanbarrossilva.ongoing.feature.authentication.AuthenticationActivity
import com.jeanbarrossilva.ongoing.feature.settings.SettingsActivity
import com.jeanbarrossilva.ongoing.platform.extensions.startActivity

internal class DefaultActivitiesBoundary(
    private val activityDetailsBoundary: ActivityDetailsBoundary
): ActivitiesBoundary {
    override fun navigateToAuthentication(context: Context) {
        context.startActivity<AuthenticationActivity>()
    }

    override fun navigateToSettings(context: Context, user: ContextualUser) {
        SettingsActivity.start(context, user)
    }

    override fun navigateToActivityDetails(
        context: Context,
        sessionManager: SessionManager,
        activityRegistry: ActivityRegistry,
        observation: Observation,
        fetcher: ContextualActivitiesFetcher,
        activityId: String
    ) {
        ActivityDetailsBridge.cross(
            context,
            sessionManager,
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