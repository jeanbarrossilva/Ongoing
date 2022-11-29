package com.jeanbarrossilva.ongoing.feature.activitydetails.bridge

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary

object ActivityDetailsBridge {
    private var session: Session? = null
    private var activityRegistry: ActivityRegistry? = null
    private var observation: Observation? = null
    private var fetcher: ContextualActivitiesFetcher? = null
    private var boundary: ActivityDetailsBoundary? = null
    private var isCrossed = false

    fun cross(
        context: Context?,
        session: Session,
        activityRegistry: ActivityRegistry,
        observation: Observation,
        fetcher: ContextualActivitiesFetcher,
        boundary: ActivityDetailsBoundary,
        crossing: ActivityDetailsBridgeCrossing
    ) {
        assertNotCrossed()
        context?.let {
            assign(session, activityRegistry, observation, fetcher, boundary)
            crossing.onCross(it)
        }
    }

    internal fun getSession(): Session {
        return requireNotNull(session)
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
        session = null
        activityRegistry = null
        observation = null
        fetcher = null
        boundary = null
    }

    private fun assertNotCrossed() {
        if (isCrossed) {
            throw IllegalStateException("Cannot cross the bridge twice.")
        }
    }

    private fun assign(
        session: Session,
        activityRegistry: ActivityRegistry,
        observation: Observation,
        fetcher: ContextualActivitiesFetcher,
        boundary: ActivityDetailsBoundary
    ) {
        this.session = session
        this.activityRegistry = activityRegistry
        this.observation = observation
        this.fetcher = fetcher
        this.boundary = boundary
    }
}