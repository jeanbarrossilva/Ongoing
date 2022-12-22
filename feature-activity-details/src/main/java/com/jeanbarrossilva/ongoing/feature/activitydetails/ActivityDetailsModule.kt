package com.jeanbarrossilva.ongoing.feature.activitydetails

import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.feature.activitydetails.observation.ActivityDetailsObservation
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val activityDetailsModule = module {
    single<Observation> {
        ActivityDetailsObservation(
            androidContext(),
            sessionManager = get(),
            userRepository = get(),
            activityRegistry = get(),
            boundary = get(),
            fetcher = get()
        )
    }
}