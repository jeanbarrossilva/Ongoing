package com.jeanbarrossilva.ongoing.platform.registry.activity

import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.platform.registry.activity.registry.RoomActivityRegistry
import kotlinx.coroutines.flow.first

class RoomActivityOwnershipManager internal constructor(private val session: Session) {
    internal suspend fun start(activityRegistry: RoomActivityRegistry) {
        session.getUser().collect { user ->
            with(activityRegistry) {
                activities.first().forEach { activity ->
                    recorder.ownerUserId(activity.id, user?.id)
                }
            }
        }
    }
}