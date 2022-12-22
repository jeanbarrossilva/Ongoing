package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.platform.registry.OngoingDatabase
import com.jeanbarrossilva.ongoing.platform.registry.activity.RoomActivityOwnershipManager
import com.jeanbarrossilva.ongoing.platform.registry.activity.RoomActivityRegistry

fun OngoingDatabase.getActivityRegistry(sessionManager: SessionManager): RoomActivityRegistry {
    val ownershipManager = RoomActivityOwnershipManager(sessionManager)
    return RoomActivityRegistry(
        coroutineScope,
        sessionManager,
        ownershipManager,
        activityDao,
        statusDao,
        observerDao
    )
}