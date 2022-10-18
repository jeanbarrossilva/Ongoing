package com.jeanbarrossilva.ongoing.app.module

import com.jeanbarrossilva.ongoing.app.boundary.DefaultActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import org.koin.dsl.module

internal val boundaryModule = module {
    single<ActivitiesBoundary> {
        DefaultActivitiesBoundary()
    }
}