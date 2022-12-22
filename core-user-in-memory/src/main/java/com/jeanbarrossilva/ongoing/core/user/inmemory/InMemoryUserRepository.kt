package com.jeanbarrossilva.ongoing.core.user.inmemory

import com.jeanbarrossilva.ongoing.core.user.User
import com.jeanbarrossilva.ongoing.core.user.UserRepository

class InMemoryUserRepository : UserRepository {
    private val users = listOf(User.sample)

    override suspend fun getUserById(id: String): User? {
        return users.find {
            it.id == id
        }
    }
}