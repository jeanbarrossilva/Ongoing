package com.jeanbarrossilva.ongoing.feature.authentication.prompter

import android.content.Context
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.platform.extensions.onFirstRun

class AuthenticationPrompter internal constructor(private val sessionManager: SessionManager) {
    suspend fun prompt(context: Context, listener: OnPromptListener) {
        context.onFirstRun {
            promptIfUnauthenticated(listener)
        }
    }

    private fun promptIfUnauthenticated(listener: OnPromptListener) {
        val isNotLoggedIn = sessionManager.session() is Session.SignedOut
        if (isNotLoggedIn) {
            listener.onPrompt()
        }
    }
}