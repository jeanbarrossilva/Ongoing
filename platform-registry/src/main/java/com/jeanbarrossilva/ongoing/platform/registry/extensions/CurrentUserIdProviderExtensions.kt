package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.platform.registry.authorization.CurrentUserIdProvider
import kotlinx.coroutines.flow.first

operator fun CurrentUserIdProvider.Companion.invoke(session: Session): CurrentUserIdProvider {
    return CurrentUserIdProvider {
        session.getUser().first()?.id
    }
}