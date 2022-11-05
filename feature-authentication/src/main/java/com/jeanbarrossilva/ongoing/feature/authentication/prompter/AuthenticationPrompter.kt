package com.jeanbarrossilva.ongoing.feature.authentication.prompter

import com.jeanbarrossilva.ongoing.core.session.SessionManager
import kotlinx.coroutines.flow.first

class AuthenticationPrompter(private val sessionManager: SessionManager) {
    suspend fun prompt(listener: OnPromptListener) {
        val isNotLoggedIn = sessionManager.getUser().first() == null
        if (isNotLoggedIn) {
            listener.onPrompt()
        }
    }
}