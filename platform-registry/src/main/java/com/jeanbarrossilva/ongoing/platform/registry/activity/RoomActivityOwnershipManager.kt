package com.jeanbarrossilva.ongoing.platform.registry.activity

import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager

internal class RoomActivityOwnershipManager(private val sessionManager: SessionManager) {
    internal suspend fun start(activityRegistry: RoomActivityRegistry) {
        sessionManager.attach<Session.SignedIn> { session ->
            with(activityRegistry) {
                getActivities().forEach { activity ->
                    recorder.ownerUserId(activity.id, session.userId)
                }
            }
        }
    }
}