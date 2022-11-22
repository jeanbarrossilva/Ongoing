package com.jeanbarrossilva.ongoing.feature.activitydetails

import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val activityDetailsModule = module {
    single<Observation> {
        ActivityDetailsObservation(
            androidContext(),
            session = get(),
            userRepository = get(),
            activityRegistry = get()
        )
    }
}