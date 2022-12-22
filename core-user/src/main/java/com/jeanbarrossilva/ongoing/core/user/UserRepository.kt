package com.jeanbarrossilva.ongoing.core.user

interface UserRepository {
    suspend fun getUserById(id: String): User?
}