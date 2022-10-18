package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity

suspend fun Collection<Activity>.mapToContextualActivity(userRepository: UserRepository):
    List<ContextualActivity> {
    return map {
        it.toContextualActivity(userRepository)
    }
}