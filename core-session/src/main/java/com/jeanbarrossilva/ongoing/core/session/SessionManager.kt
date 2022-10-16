package com.jeanbarrossilva.ongoing.core.session

import com.jeanbarrossilva.ongoing.core.session.user.User
import kotlinx.coroutines.flow.Flow

interface SessionManager {
    suspend fun authenticate()

    suspend fun getUser(): Flow<User?>

    suspend fun logOut()
}