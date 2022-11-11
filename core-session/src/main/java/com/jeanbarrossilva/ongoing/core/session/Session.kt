package com.jeanbarrossilva.ongoing.core.session

import com.jeanbarrossilva.ongoing.core.session.user.User
import kotlinx.coroutines.flow.Flow

interface Session {
    suspend fun logIn()

    fun getUser(): Flow<User?>

    suspend fun logOut()
}