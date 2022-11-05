package com.jeanbarrossilva.ongoing.platform.session

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.platform.session.extensions.toUser
import java.lang.ref.WeakReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class GoogleSignInSessionManager(private val contextRef: WeakReference<Context>): SessionManager {
    private var user = MutableStateFlow<User?>(null)

    private val context
        get() = contextRef.get()

    constructor(context: Context): this(WeakReference(context))

    override suspend fun authenticate() {
        user.value = retrieveUser() ?: signIn()
    }

    override suspend fun getUser(): Flow<User?> {
        return user.asStateFlow()
    }

    override suspend fun logOut() {
        user.value = null
    }

    private suspend fun signIn(): User? {
        return context?.run {
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .build()
                .let { GoogleSignIn.getClient(this, it) }
                .silentSignIn()
                .await()
                .toUser()
        }
    }

    private fun retrieveUser(): User? {
        return context?.let {
            GoogleSignIn.getLastSignedInAccount(it)?.toUser()
        }
    }
}