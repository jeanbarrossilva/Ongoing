package com.jeanbarrossilva.ongoing.feature.activities.extensions

import com.jeanbarrossilva.ongoing.core.session.OnSessionChangeListener
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Gets a [Flow] of [User]s that gets emitted to each time a [Session] is
 * [started][Session.SignedOut.start] or [ended][Session.SignedIn.end].
 *
 * @param userRepository [UserRepository] to get the [User] from.
 **/
internal fun SessionManager.getUser(userRepository: UserRepository): Flow<User?> {
    return callbackFlow {
        val listener = OnSessionChangeListener {
            when (it) {
                is Session.SignedIn -> send(userRepository.getUserById(it.userId))
                is Session.SignedOut -> send(null)
            }
        }
        attach(listener)
        awaitClose { detach(listener) }
    }
}