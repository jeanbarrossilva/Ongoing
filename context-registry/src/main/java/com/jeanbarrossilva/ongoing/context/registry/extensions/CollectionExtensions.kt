package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository

suspend fun Collection<Activity>.mapToContextualActivity(
    sessionManager: SessionManager,
    userRepository: UserRepository
): List<ContextualActivity> {
    return map {
        it.toContextualActivity(sessionManager, userRepository)
    }
}