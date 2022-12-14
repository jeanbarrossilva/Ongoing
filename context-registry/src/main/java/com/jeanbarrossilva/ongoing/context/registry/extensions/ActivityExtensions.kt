package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.user.extensions.toContextualUser
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.core.user.User
import com.jeanbarrossilva.ongoing.core.user.UserRepository

suspend fun Activity.toContextualActivity(
    sessionManager: SessionManager,
    userRepository: UserRepository
): ContextualActivity {
    val owner = ownerUserId.let { userRepository.getUserById(it) }?.toContextualUser()
        ?: throw IllegalArgumentException("No user $ownerUserId found.")
    val icon = icon.toContextualIcon()
    val statuses = statuses.map(Status::toContextualStatus)
    val observers =
        observerUserIds.mapNotNull { userRepository.getUserById(it) }.map(User::toContextualUser)
    val isObserving = sessionManager.session<Session.SignedIn>()?.userId in observerUserIds
    return ContextualActivity(id, owner, name, icon, statuses, observers, isObserving)
}