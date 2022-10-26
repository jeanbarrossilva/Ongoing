package com.jeanbarrossilva.ongoing.app.extensions

import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.platform.registry.authorization.CurrentUserIdProvider
import kotlinx.coroutines.flow.first

internal operator fun CurrentUserIdProvider.Companion.invoke(sessionManager: SessionManager):
    CurrentUserIdProvider {
    return CurrentUserIdProvider {
        sessionManager.getUser().first()!!.id
    }
}