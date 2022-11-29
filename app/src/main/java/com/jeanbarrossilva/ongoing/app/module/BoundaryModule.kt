package com.jeanbarrossilva.ongoing.app.module

import com.jeanbarrossilva.ongoing.app.boundary.DefaultActivitiesBoundary
import com.jeanbarrossilva.ongoing.app.boundary.DefaultActivityDetailsBoundary
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary
import org.koin.dsl.module

internal val boundaryModule = module {
    single<ActivityDetailsBoundary> { DefaultActivityDetailsBoundary() }
    single<ActivitiesBoundary> { DefaultActivitiesBoundary(activityDetailsBoundary = get()) }
}