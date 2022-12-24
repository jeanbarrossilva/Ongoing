package com.jeanbarrossilva.ongoing.feature.activities

import com.jeanbarrossilva.ongoing.feature.activities.inmemory.InMemoryActivitiesGateway
import org.koin.dsl.module

val activitiesModule = module {
    single<ActivitiesGateway> {
        InMemoryActivitiesGateway(sessionManager = get(), userRepository = get(), fetcher = get())
    }
}