package com.jeanbarrossilva.ongoing.core.session.inmemory

import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import kotlinx.coroutines.flow.first

class InMemoryUserRepository(private val sessionManager: SessionManager): UserRepository {
    override suspend fun getUserById(id: String): User? {
        return getUsers().find {
            it.id == id
        }
    }

    private suspend fun getUsers(): List<User> {
        val currentUser = sessionManager.getUser().first()
        return listOfNotNull(currentUser)
    }
}