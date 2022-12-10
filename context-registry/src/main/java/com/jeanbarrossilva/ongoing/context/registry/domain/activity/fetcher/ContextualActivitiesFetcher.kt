package com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import java.util.concurrent.ConcurrentLinkedQueue

class ContextualActivitiesFetcher(
    internal val session: Session,
    internal val userRepository: UserRepository,
    internal val activityRegistry: ActivityRegistry
) {
    private val listeners = ConcurrentLinkedQueue<OnFetchListener>()

    suspend fun fetch() {
        val activities = getActivities()
        listeners.forEach { it.onRefresh(activities) }
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