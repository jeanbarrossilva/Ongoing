package com.jeanbarrossilva.ongoing.platform.registry.activity

import com.jeanbarrossilva.ongoing.core.session.Session

class RoomActivityOwnershipManager internal constructor(private val session: Session) {
    internal suspend fun start(activityRegistry: RoomActivityRegistry) {
        session.getUser().collect { user ->
            with(activityRegistry) {
                getActivities().forEach { activity ->
                    recorder.ownerUserId(activity.id, user?.id)
                }
            }
        }
    }
}