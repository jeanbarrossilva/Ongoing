package com.jeanbarrossilva.ongoing.app.boundary

import android.content.Context
import com.jeanbarrossilva.ongoing.app.extensions.toActivityEditingMode
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class DefaultActivityDetailsBoundary(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry
): ActivityDetailsBoundary {
    override fun navigateToActivityEditing(
        coroutineScope: CoroutineScope,
        context: Context,
        activityId: String
    ) {
        coroutineScope.launch {
            val mode = activityRegistry
                .getActivityById(activityId)
                ?.toContextualActivity(sessionManager, userRepository)
                .toActivityEditingMode()
            ActivityEditingActivity.start(context, mode)
        }
    }
}