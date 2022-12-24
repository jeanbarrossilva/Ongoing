package com.jeanbarrossilva.ongoing.core.registry.activity

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.OnStatusChangeListener
import com.jeanbarrossilva.ongoing.core.registry.observation.Change
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation

data class Activity(
    val id: String,
    val ownerUserId: String,
    val name: String,
    val icon: Icon,
    val observerUserIds: List<String>
) {
    var statuses = Status.default
        private set(value) {
            value.ifEmpty { return }
            field = value
        }

    var status
        get() = statuses.last()
        set(value) { statuses = statuses + value }

    constructor(
        id: String,
        ownerUserId: String,
        name: String,
        icon: Icon,
        statuses: List<Status>,
        observerUserIds: List<String>
    ): this(id, ownerUserId, name, icon, observerUserIds) {
        this.statuses = statuses
    }

    abstract class Recorder {
        protected abstract val registry: ActivityRegistry

        suspend fun name(userId: String, activityId: String, name: String) {
            assertOwnership(activityId, userId)
            onName(activityId, name)
        }

        suspend fun icon(userId: String, activityId: String, icon: Icon) {
            assertOwnership(activityId, userId)
            onIcon(activityId, icon)
        }

        suspend fun status(userId: String, activityId: String, status: Status) {
            assertOwnership(activityId, userId)
            onStatus(activityId, status)
        }

        abstract suspend fun doOnStatusChange(listener: OnStatusChangeListener)

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

    interface Observer {
        suspend fun attach(userId: String, activityId: String, observation: Observation)

        suspend fun notify(changerUserId: String?, activityId: String, change: Change)

        suspend fun detach(userId: String, activityId: String)

        suspend fun clear()
    }
}