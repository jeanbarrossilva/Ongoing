package com.jeanbarrossilva.ongoing.app.boundary

import android.content.Context
import com.jeanbarrossilva.ongoing.context.user.extensions.toContextualUser
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.core.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingMode
import com.jeanbarrossilva.ongoing.feature.authentication.AuthenticationActivity
import com.jeanbarrossilva.ongoing.feature.settings.SettingsActivity
import com.jeanbarrossilva.ongoing.platform.extensions.startActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class DefaultActivitiesBoundary(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository
): ActivitiesBoundary {
    override fun navigateToAuthentication(context: Context) {
        context.startActivity<AuthenticationActivity>()
    }

    override fun navigateToSettings(context: Context, coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            sessionManager
                .session<Session.SignedIn>()
                ?.userId
                ?.let { userRepository.getUserById(it) }
                ?.toContextualUser()
                ?.let { SettingsActivity.start(context, it) }
        }
    }

    override fun navigateToActivityDetails(context: Context, activityId: String) {
        ActivityDetailsActivity.start(context, activityId)
    }

    override fun navigateToActivityEditing(context: Context) {
        ActivityEditingActivity.start(context, ActivityEditingMode.Addition)
    }
}