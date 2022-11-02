package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity

suspend fun Activity.toContextualActivity(userRepository: UserRepository):
    ContextualActivity {
    val owner = userRepository.getUserById(ownerUserId) ?: throw IllegalArgumentException()
    val icon = icon.toContextualIcon()
    val statuses = statuses.map(Status::toContextualStatus)
    return ContextualActivity(id, owner, name, icon, statuses)
}