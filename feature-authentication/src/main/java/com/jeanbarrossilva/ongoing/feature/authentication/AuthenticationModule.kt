package com.jeanbarrossilva.ongoing.feature.authentication

import com.jeanbarrossilva.ongoing.feature.authentication.prompter.AuthenticationPrompter
import org.koin.dsl.module

 val authenticationModule = module {
    single {
        AuthenticationPrompter(session = get())
    }
}