package com.jeanbarrossilva.ongoing.feature.activities.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activities.context.ContextualActivity

internal suspend fun Collection<Activity>.mapToContextualActivity(userRepository: UserRepository):
    List<ContextualActivity> {
    return map {
        it.toContextualActivity(userRepository)
    }
}