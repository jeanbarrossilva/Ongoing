package com.jeanbarrossilva.ongoing.core.session.inmemory

import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.inmemory.extensions.getValue
import com.jeanbarrossilva.ongoing.core.session.inmemory.extensions.setValue
import com.jeanbarrossilva.ongoing.core.session.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemorySession: Session {
    private val userFlow = MutableStateFlow<User?>(null)
    private var user by userFlow

    override suspend fun logIn() {
        user = Companion.user
    }

    override suspend fun getUser(): Flow<User?> {
        return userFlow
    }

    override suspend fun logOut() {
        user = null
    }

    companion object {
        internal val user = User.sample
    }
}