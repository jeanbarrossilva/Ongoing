package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.platform.registry.OngoingDatabase
import com.jeanbarrossilva.ongoing.platform.registry.activity.RoomActivityOwnershipManager
import com.jeanbarrossilva.ongoing.platform.registry.activity.RoomActivityRegistry

fun OngoingDatabase.getActivityRegistry(session: Session): RoomActivityRegistry {
    val ownershipManager = RoomActivityOwnershipManager(session)
    return RoomActivityRegistry(
        coroutineScope,
        session,
        ownershipManager,
        activityDao,
        statusDao,
        observerDao
    )
}