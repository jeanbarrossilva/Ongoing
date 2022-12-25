package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.platform.registry.OngoingDatabase
import com.jeanbarrossilva.ongoing.platform.registry.activity.RoomActivityRegistry

fun OngoingDatabase.getActivityRegistry(sessionManager: SessionManager): RoomActivityRegistry {
    return RoomActivityRegistry(sessionManager, activityDao, statusDao, observerDao)
}