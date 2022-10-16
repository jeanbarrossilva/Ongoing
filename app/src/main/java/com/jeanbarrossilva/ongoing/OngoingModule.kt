package com.jeanbarrossilva.ongoing

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.inmemory.InMemoryActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import org.koin.dsl.module

internal val ongoingModule = module {
    single<UserRepository> { InMemoryUserRepository() }
    single<ActivityRegistry> { InMemoryActivityRegistry() }
}