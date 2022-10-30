package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.platform.registry.authorization.CurrentUserIdProvider
import kotlinx.coroutines.flow.first

operator fun CurrentUserIdProvider.Companion.invoke(sessionManager: SessionManager):
    CurrentUserIdProvider {
    return CurrentUserIdProvider {
        sessionManager.getUser().first()!!.id
    }
}