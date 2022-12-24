package com.jeanbarrossilva.ongoing.core.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status

abstract class ActivityRecorder {
    protected abstract val registry: ActivityRegistry

    suspend fun ownerUserId(activityId: String, ownerUserId: String?) {
        assertOwnership(activityId, "")
        onOwnerUserId(activityId, ownerUserId)
    }

    suspend fun name(activityId: String, name: String) {
        assertOwnership(activityId, "")
        onName(activityId, name)
    }

    suspend fun icon(activityId: String, icon: Icon) {
        assertOwnership(activityId, "")
        onIcon(activityId, icon)
    }

    suspend fun status(activityId: String, status: Status) {
        assertOwnership(activityId, "")
        onStatus(activityId, status)
    }

    abstract suspend fun doOnStatusChange(listener: OnStatusChangeListener)

    protected abstract suspend fun onOwnerUserId(activityId: String, ownerUserId: String?)

    protected abstract suspend fun onName(activityId: String, name: String)

    protected abstract suspend fun onIcon(activityId: String, icon: Icon)

    protected abstract suspend fun onStatus(activityId: String, status: Status)

    private suspend fun assertOwnership(activityId: String, userId: String) {
        val activity = registry.getActivityByIdOrThrow(activityId)
        if (activity.ownerUserId != userId) {
            throw IllegalArgumentException(
                "Only the owner of activity $activityId can record to it."
            )
        }
    }
}