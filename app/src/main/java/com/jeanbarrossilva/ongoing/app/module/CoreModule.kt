package com.jeanbarrossilva.ongoing.app.module

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySession
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.platform.registry.authorization.CurrentUserIdProvider
import com.jeanbarrossilva.ongoing.platform.registry.extensions.database
import com.jeanbarrossilva.ongoing.platform.registry.extensions.getActivityRegistry
import com.jeanbarrossilva.ongoing.platform.registry.extensions.invoke
import org.koin.dsl.module

internal val coreModule = module {
    single<Session> { InMemorySession() }
    single<UserRepository> { InMemoryUserRepository(session = get()) }
    single<ActivityRegistry> {
        database.getActivityRegistry(CurrentUserIdProvider(session = get()))
    }
}