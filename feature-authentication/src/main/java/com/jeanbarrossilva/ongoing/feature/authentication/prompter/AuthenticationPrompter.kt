package com.jeanbarrossilva.ongoing.feature.authentication.prompter

import android.content.Context
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.platform.extensions.onFirstRun
import kotlinx.coroutines.flow.first

class AuthenticationPrompter internal constructor(private val session: Session) {
    suspend fun prompt(context: Context, listener: OnPromptListener) {
        context.onFirstRun {
            promptIfUnauthenticated(listener)
        }
    }

    private suspend fun promptIfUnauthenticated(listener: OnPromptListener) {
        val isNotLoggedIn = session.getUser().first() == null
        if (isNotLoggedIn) {
            listener.onPrompt()
        }
    }
}