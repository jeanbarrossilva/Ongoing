package com.jeanbarrossilva.ongoing.core.session.inmemory

import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import kotlinx.coroutines.flow.first

class InMemoryUserRepository(private val session: Session): UserRepository {
    override suspend fun getUserById(id: String): User? {
        return getUsers().find {
            it.id == id
        }
    }

    private suspend fun getUsers(): List<User> {
        val currentUser = session.getUser().first()
        return listOfNotNull(currentUser)
    }
}