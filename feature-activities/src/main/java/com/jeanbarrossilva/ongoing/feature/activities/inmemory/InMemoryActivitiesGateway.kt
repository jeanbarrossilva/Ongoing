package com.jeanbarrossilva.ongoing.feature.activities.inmemory

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.extensions.getActivities
import com.jeanbarrossilva.ongoing.context.registry.extensions.toActivitiesOwner
import com.jeanbarrossilva.ongoing.context.registry.extensions.unregister
import com.jeanbarrossilva.ongoing.context.user.extensions.toContextualUser
import com.jeanbarrossilva.ongoing.core.session.OnSessionChangeListener
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesGateway
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesOwner
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.loadableChannelFlow
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.send
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow

internal class InMemoryActivitiesGateway(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val fetcher: ContextualActivitiesFetcher
): ActivitiesGateway {
    override suspend fun getCurrentOwner(): Flow<Loadable<ActivitiesOwner?>> {
        return loadableChannelFlow {
            val listener = OnSessionChangeListener {
                val owner = when (it) {
                    is Session.SignedOut -> null
                    is Session.SignedIn -> userRepository
                        .getUserById(it.userId)
                        ?.toContextualUser()
                        ?.toActivitiesOwner()
                }
                send(owner)
                fetch()
            }
            listener.onSessionChange(sessionManager.session())
            sessionManager.attach(listener)
            awaitClose { sessionManager.detach(listener) }
        }
    }

    override suspend fun getActivities(): Flow<Loadable<SerializableList<ContextualActivity>>> {
        return loadableChannelFlow {
            fetcher.getActivities().collect {
                send(it)
            }
        }
    }

    override suspend fun fetch() {
        fetcher.fetch()
    }

    override suspend fun unregister(activityIds: List<String>) {
        activityIds.forEach { fetcher.unregister(it) }
        fetch()
    }
}