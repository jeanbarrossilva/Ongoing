package com.jeanbarrossilva.ongoing.platform.registry.test.extensions

import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySession
import com.jeanbarrossilva.ongoing.platform.registry.OngoingDatabase
import com.jeanbarrossilva.ongoing.platform.registry.activity.registry.RoomActivityRegistry
import com.jeanbarrossilva.ongoing.platform.registry.extensions.getActivityRegistry

val OngoingDatabase.activityRegistry: RoomActivityRegistry
    get() {
        val sessionManager = InMemorySession()
        return getActivityRegistry(sessionManager)
    }