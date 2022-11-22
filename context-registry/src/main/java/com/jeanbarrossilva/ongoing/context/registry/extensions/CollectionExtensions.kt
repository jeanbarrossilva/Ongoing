package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.core.session.Session

suspend fun Collection<Activity>.mapToContextualActivity(
    session: Session,
    userRepository: UserRepository
): List<ContextualActivity> {
    return map {
        it.toContextualActivity(session, userRepository)
    }
}