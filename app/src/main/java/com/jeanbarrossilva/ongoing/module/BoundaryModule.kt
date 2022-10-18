package com.jeanbarrossilva.ongoing.module

import com.jeanbarrossilva.ongoing.boundary.DefaultActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import org.koin.dsl.module

internal val boundaryModule = module {
    single<ActivitiesBoundary> {
        DefaultActivitiesBoundary()
    }
}