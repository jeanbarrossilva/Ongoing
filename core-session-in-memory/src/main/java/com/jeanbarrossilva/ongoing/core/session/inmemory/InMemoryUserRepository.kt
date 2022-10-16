package com.jeanbarrossilva.ongoing.core.session.inmemory

import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository

class InMemoryUserRepository: UserRepository {
    override suspend fun getUserById(id: String): User? {
        return if (id == User.sample.id) User.sample else null
    }
}