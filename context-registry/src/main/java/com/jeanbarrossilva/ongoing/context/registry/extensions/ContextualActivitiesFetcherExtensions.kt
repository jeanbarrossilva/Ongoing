package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.OnFetchListener
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.loadableChannelFlow
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.map
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toSerializableList
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Clears the [activities][Activity] from the [ContextualActivitiesFetcher.activityRegistry] and
 * then [fetches][ContextualActivitiesFetcher.fetch].
 *
 * @param userId ID of the user that's performing the operation.
 **/
suspend fun ContextualActivitiesFetcher.clear(userId: String) {
    activityRegistry.clear(userId)
    fetch()
}

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

/**
 * Unregisters the [Activity] whose [Activity.id] is equal to the given [activityId] from the
 * [ContextualActivitiesFetcher.activityRegistry].
 *
 * @param activityId ID of the [Activity] to unregister.
 **/
suspend fun ContextualActivitiesFetcher.unregister(activityId: String) {
    val userId = session.getUser().filterNotNull().first().id
    activityRegistry.unregister(userId, activityId)
    fetch()
}

/**
 * Registers the given [activity] (although it'll have a different [ContextualActivity.id]) and then
 * fetches.
 *
 * @param activity [ContextualActivity] to register.
 **/
suspend fun ContextualActivitiesFetcher.register(activity: ContextualActivity) {
    val ownerUserId = session.getUser().filterNotNull().first().id
    activityRegistry.register(ownerUserId, activity)
    fetch()
}