package com.jeanbarrossilva.ongoing.core.session.inmemory

import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.inmemory.extensions.getValue
import com.jeanbarrossilva.ongoing.core.session.inmemory.extensions.setValue
import com.jeanbarrossilva.ongoing.core.session.inmemory.extensions.uuid
import com.jeanbarrossilva.ongoing.core.session.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemorySessionManager: SessionManager {
    private val userFlow = MutableStateFlow<User?>(null)
    private var user by userFlow

    override suspend fun authenticate() {
        user = Companion.user
    }

    override suspend fun getUser(): Flow<User?> {
        return userFlow
    }

    override suspend fun logOut() {
        user = null
    }

    companion object {
        private const val USER_AVATAR_URL =
            "https://en.gravatar.com/userimage/153558542/cb04b28164b6ec24f7f4cdee8d20d1c9.png"

        internal val user = User(uuid(), name = "Jean", USER_AVATAR_URL)
    }
}