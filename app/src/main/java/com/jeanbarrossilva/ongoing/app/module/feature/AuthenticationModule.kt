package com.jeanbarrossilva.ongoing.app.module.feature

import com.jeanbarrossilva.ongoing.feature.authentication.prompter.AuthenticationPrompter
import org.koin.dsl.module

internal val authenticationModule = module {
    single {
        AuthenticationPrompter(sessionManager = get())
    }
}