package com.jeanbarrossilva.ongoing.app.module

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySessionManager
import com.jeanbarrossilva.ongoing.core.user.UserRepository
import com.jeanbarrossilva.ongoing.core.user.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.platform.registry.extensions.database
import com.jeanbarrossilva.ongoing.platform.registry.extensions.getActivityRegistry
import org.koin.dsl.module

internal val coreModule = module {
    single<SessionManager> { InMemorySessionManager() }
    single<UserRepository> { InMemoryUserRepository() }
    single<ActivityRegistry> { database.getActivityRegistry(sessionManager = get()) }
}