package com.jeanbarrossilva.ongoing.core.session.user

interface UserRepository {
    suspend fun getUserById(id: String): User?
}