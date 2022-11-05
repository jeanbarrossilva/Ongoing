package com.jeanbarrossilva.ongoing.feature.authentication.prompter

import android.content.Context
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.platform.extensions.onFirstRun
import kotlinx.coroutines.flow.first

class AuthenticationPrompter(private val sessionManager: SessionManager) {
    suspend fun prompt(context: Context, listener: OnPromptListener) {
        context.onFirstRun {
            promptIfUnauthenticated(listener)
        }
    }

    private suspend fun promptIfUnauthenticated(listener: OnPromptListener) {
        val isNotLoggedIn = sessionManager.getUser().first() == null
        if (isNotLoggedIn) {
            listener.onPrompt()
        }
    }
}