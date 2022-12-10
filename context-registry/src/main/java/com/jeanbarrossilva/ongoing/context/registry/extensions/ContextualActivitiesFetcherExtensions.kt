package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.OnFetchListener
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.loadableChannelFlow
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.map
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toSerializableList
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Gets the given [ContextualActivitiesFetcher]'s up-to-date [Loadable][Loadable]
 * [SerializableList] of [contextual activities][ContextualActivity] wrapped in a [Flow].
 **/
fun ContextualActivitiesFetcher.getActivities():
    Flow<Loadable<SerializableList<ContextualActivity>>> {
    return loadableChannelFlow {
        val listener = OnFetchListener { send(Loadable.Loaded(it.toSerializableList())) }
        attach(listener)
        awaitClose { detach(listener) }
    }
}

/**
 * Gets the [ContextualActivity] whose [ContextualActivity.id] matches the given [activityId],
 * mapping it to a [Flow].
 *
 * @param activityId [ContextualActivity.id] of the [ContextualActivity] to be obtained.
 **/
fun ContextualActivitiesFetcher.getActivity(activityId: String):
    Flow<Loadable<ContextualActivity>> {
    return getActivities().map {
        it.map {
            activityRegistry
                .getActivityById(activityId)
                ?.toContextualActivity(session, userRepository)
                ?: throw IllegalArgumentException("Could not find activity $activityId.")
        }
    }
}