package com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository

class ContextualActivitiesFetcher(
    private val session: Session,
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry
) {
    private val listeners = mutableListOf<OnFetchListener>()

    suspend fun fetch() {
        listeners.forEach {
            it.onRefresh(getActivities())
        }
    }

    fun attach(listener: OnFetchListener) {
        listeners.add(listener)
    }

    fun detach(listener: OnFetchListener) {
        listeners.remove(listener)
    }

    private suspend fun getActivities(): List<ContextualActivity> {
        return activityRegistry.getActivities().map {
            it.toContextualActivity(session, userRepository)
        }
    }
}