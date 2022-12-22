package com.jeanbarrossilva.ongoing.core.session.inmemory

import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository

class InMemoryUserRepository : UserRepository {
    private val users = listOf(User.sample)

    override suspend fun getUserById(id: String): User? {
        return users.find {
            it.id == id
        }
    }
}