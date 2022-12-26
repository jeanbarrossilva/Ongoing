package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry

/**
 * Registers each of the given [activities] with the specified [ownerUserId].
 *
 * @param ownerUserId ID of the user that's the owner of the [activities].
 * @param activities [Activities][ContextualActivity] to be registered.
 **/
@Suppress("KDocUnresolvedReference")
suspend fun ActivityRegistry.register(ownerUserId: String, activities: List<ContextualActivity>):
    List<String> {
    return mutableListOf<String>()
        .apply { for (activity in activities) add(register(ownerUserId, activity)) }
        .toList()
}

/**
 * Registers the given [activity] with the specified [ownerUserId].
 *
 * @param ownerUserId ID of the user that's the owner of the [activity].
 * @param activity [ContextualActivity] to be registered.
 **/
suspend fun ActivityRegistry.register(ownerUserId: String, activity: ContextualActivity): String {
    val statuses = activity.statuses.map(ContextualStatus::toStatus)
    return register(ownerUserId, activity.name, statuses)
}