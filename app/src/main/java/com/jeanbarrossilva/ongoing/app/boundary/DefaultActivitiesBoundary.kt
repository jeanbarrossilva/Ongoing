package com.jeanbarrossilva.ongoing.app.boundary

import android.content.Context
import com.jeanbarrossilva.ongoing.context.user.ContextualUser
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingMode
import com.jeanbarrossilva.ongoing.feature.authentication.AuthenticationActivity
import com.jeanbarrossilva.ongoing.feature.settings.SettingsActivity
import com.jeanbarrossilva.ongoing.platform.extensions.startActivity

internal class DefaultActivitiesBoundary: ActivitiesBoundary {
    override fun navigateToAuthentication(context: Context) {
        context.startActivity<AuthenticationActivity>()
    }

    override fun navigateToSettings(context: Context, user: ContextualUser) {
        SettingsActivity.start(context, user)
    }

    override fun navigateToActivityDetails(context: Context, activityId: String) {
        ActivityDetailsActivity.start(context, activityId)
    }

    override fun navigateToActivityEditing(context: Context) {
        ActivityEditingActivity.start(context, ActivityEditingMode.Addition)
    }
}