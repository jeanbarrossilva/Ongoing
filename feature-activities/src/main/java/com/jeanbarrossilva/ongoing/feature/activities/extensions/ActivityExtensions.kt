package com.jeanbarrossilva.ongoing.feature.activities.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activities.context.ContextualActivity

internal suspend fun Activity.toContextualActivity(userRepository: UserRepository):
    ContextualActivity {
    val owner = userRepository.getUserById(ownerUserId) ?: throw IllegalArgumentException()
    val icon = icon.toContextualIcon()
    val statuses = statuses.map(Status::toContextualStatus)
    val currentStatus = currentStatus.toContextualStatus()
    return ContextualActivity(id, owner, name, icon, statuses, currentStatus)
}