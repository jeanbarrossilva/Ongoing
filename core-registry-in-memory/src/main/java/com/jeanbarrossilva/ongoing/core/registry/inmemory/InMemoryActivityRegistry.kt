package com.jeanbarrossilva.ongoing.core.registry.inmemory

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.registry.inmemory.extensions.uuid

class InMemoryActivityRegistry: ActivityRegistry() {
    internal val activities = mutableListOf<Activity>()

    override val recorder = InMemoryActivityRecorder(this)

    override val observer
        get() = TODO("Not yet implemented")

    override suspend fun getActivities(): List<Activity> {
        return activities.toList()
    }

    override suspend fun getActivityById(id: String): Activity? {
        return getActivities().find {
            it.id == id
        }
    }

    override suspend fun onRegister(ownerUserId: String, name: String, statuses: List<Status>):
        String {
        val id = uuid()
        val activity =
            Activity(id, ownerUserId, name, Icon.default, statuses, observerUserIds = emptyList())
        activities += activity
        return id
    }

    override suspend fun onUnregister(userId: String, activityId: String) {
        activities -= getActivities().first {
            it.id == activityId
        }
    }
}