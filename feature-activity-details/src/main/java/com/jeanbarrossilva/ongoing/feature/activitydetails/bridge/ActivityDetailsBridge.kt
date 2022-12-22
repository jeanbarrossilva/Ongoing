package com.jeanbarrossilva.ongoing.feature.activitydetails.bridge

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary

object ActivityDetailsBridge {
    private var sessionManager: SessionManager? = null
    private var activityRegistry: ActivityRegistry? = null
    private var observation: Observation? = null
    private var fetcher: ContextualActivitiesFetcher? = null
    private var boundary: ActivityDetailsBoundary? = null
    private var isCrossed = false

    fun cross(
        context: Context?,
        sessionManager: SessionManager,
        activityRegistry: ActivityRegistry,
        observation: Observation,
        fetcher: ContextualActivitiesFetcher,
        boundary: ActivityDetailsBoundary,
        crossing: ActivityDetailsBridgeCrossing
    ) {
        if (!isCrossed && context != null) {
            assign(sessionManager, activityRegistry, observation, fetcher, boundary)
            crossing.onCross(context)
        }
    }

    internal fun getSessionManager(): SessionManager {
        return requireNotNull(sessionManager)
    }

    internal fun getActivityRegistry(): ActivityRegistry {
        return requireNotNull(activityRegistry)
    }

    internal fun getObservation(): Observation {
        return requireNotNull(observation)
    }

    internal fun getFetcher(): ContextualActivitiesFetcher {
        return requireNotNull(fetcher)
    }

    internal fun getBoundary(): ActivityDetailsBoundary {
        return requireNotNull(boundary)
    }

    internal fun clear() {
        sessionManager = null
        activityRegistry = null
        observation = null
        fetcher = null
        boundary = null
    }

    private fun assign(
        sessionManager: SessionManager,
        activityRegistry: ActivityRegistry,
        observation: Observation,
        fetcher: ContextualActivitiesFetcher,
        boundary: ActivityDetailsBoundary
    ) {
        this.sessionManager = sessionManager
        this.activityRegistry = activityRegistry
        this.observation = observation
        this.fetcher = fetcher
        this.boundary = boundary
    }
}