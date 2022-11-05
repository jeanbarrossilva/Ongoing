package com.jeanbarrossilva.ongoing.app.module

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.platform.registry.authorization.CurrentUserIdProvider
import com.jeanbarrossilva.ongoing.platform.registry.extensions.database
import com.jeanbarrossilva.ongoing.platform.registry.extensions.getActivityRegistry
import com.jeanbarrossilva.ongoing.platform.registry.extensions.invoke
import com.jeanbarrossilva.ongoing.platform.session.GoogleSignInSessionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val coreModule = module {
    single<SessionManager> { GoogleSignInSessionManager(androidContext()) }
    single<UserRepository> { InMemoryUserRepository(sessionManager = get()) }
    single<ActivityRegistry> {
        database.getActivityRegistry(CurrentUserIdProvider(sessionManager = get()))
    }
}