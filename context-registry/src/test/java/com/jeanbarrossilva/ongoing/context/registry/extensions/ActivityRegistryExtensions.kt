package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry

internal suspend fun ActivityRegistry.register(ownerUserId: String, activity: ContextualActivity):
    String {
    val statuses = activity.statuses.map(ContextualStatus::toStatus)
    return register(ownerUserId, activity.name, statuses)
}