package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import kotlinx.coroutines.flow.first

suspend fun Activity.toContextualActivity(session: Session, userRepository: UserRepository):
    ContextualActivity {
    val owner = ownerUserId?.let { userRepository.getUserById(it) }
    val icon = icon.toContextualIcon()
    val statuses = statuses.map(Status::toContextualStatus)
    val observers = observerUserIds.mapNotNull { userRepository.getUserById(it) }
    val isObserving = session.getUser().first() in observers
    return ContextualActivity(id, owner, name, icon, statuses, observers, isObserving)
}