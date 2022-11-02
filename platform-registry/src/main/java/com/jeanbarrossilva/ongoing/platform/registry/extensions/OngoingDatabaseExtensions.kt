package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.platform.registry.OngoingDatabase
import com.jeanbarrossilva.ongoing.platform.registry.activity.registry.RoomActivityRegistry
import com.jeanbarrossilva.ongoing.platform.registry.authorization.CurrentUserIdProvider

internal val OngoingDatabase.activityRegistry: RoomActivityRegistry
    get() {
        val currentUserId = uuid()
        val currentUserIdProvider = CurrentUserIdProvider { currentUserId }
        return getActivityRegistry(currentUserIdProvider)
    }

fun OngoingDatabase.getActivityRegistry(currentUserIdProvider: CurrentUserIdProvider):
    RoomActivityRegistry {
    return RoomActivityRegistry(activityDao, statusDao, currentUserIdProvider)
}