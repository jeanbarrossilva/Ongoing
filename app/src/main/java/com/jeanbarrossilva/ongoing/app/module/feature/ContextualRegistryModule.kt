package com.jeanbarrossilva.ongoing.app.module.feature

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import org.koin.dsl.module

internal val contextualRegistryModule = module {
    single {
        ContextualActivitiesFetcher(
            sessionManager = get(),
            userRepository = get(),
            activityRegistry = get()
        )
    }
}