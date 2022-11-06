package com.jeanbarrossilva.ongoing.app.module

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySessionManager
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.platform.registry.authorization.CurrentUserIdProvider
import com.jeanbarrossilva.ongoing.platform.registry.extensions.database
import com.jeanbarrossilva.ongoing.platform.registry.extensions.getActivityRegistry
import com.jeanbarrossilva.ongoing.platform.registry.extensions.invoke
import org.koin.dsl.module

internal val coreModule = module {
    single<SessionManager> { InMemorySessionManager() }
    single<UserRepository> { InMemoryUserRepository(sessionManager = get()) }
    single<ActivityRegistry> {
        database.getActivityRegistry(CurrentUserIdProvider(sessionManager = get()))
    }
}