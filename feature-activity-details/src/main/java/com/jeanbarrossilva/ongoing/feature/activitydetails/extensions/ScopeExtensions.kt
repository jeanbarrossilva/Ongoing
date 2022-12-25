package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.infra.ActivityDetailsGateway
import com.jeanbarrossilva.ongoing.feature.activitydetails.infra.inmemory.InMemoryActivityDetailsGateway
import com.jeanbarrossilva.ongoing.feature.activitydetails.observation.ActivityDetailsObservation
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.scope.Scope

context(Module)
internal fun Scope.getActivityDetailsGateway(
    activityId: String,
    androidActivity: ActivityDetailsActivity,
    onObservationToggle: (isObserving: Boolean) -> Unit
): ActivityDetailsGateway {
    val sessionManager = get<SessionManager>()
    val activityRegistry = get<ActivityRegistry>()
    val observation = ActivityDetailsObservation(
        androidContext(),
        sessionManager,
        userRepository = get(),
        activityRegistry
    )
    return InMemoryActivityDetailsGateway(
        sessionManager,
        activityRegistry,
        observation,
        activityId,
        androidActivity,
        fetcher = get(),
        onObservationToggle
    )
}