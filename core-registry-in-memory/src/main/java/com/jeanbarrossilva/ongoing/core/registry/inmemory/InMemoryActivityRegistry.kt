package com.jeanbarrossilva.ongoing.core.registry.inmemory

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.registry.inmemory.extensions.uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class InMemoryActivityRegistry: ActivityRegistry() {
    internal val _activities = MutableStateFlow(emptyList<Activity>())

    override val recorder = InMemoryActivityRecorder(this)
    override val observer: Activity.Observer
        get() = TODO("Not yet implemented")
    override val activities = _activities.asStateFlow()

    override suspend fun getActivityById(id: String): Flow<Activity?> {
        return activities.map {
            it.find { activity ->
                activity.id == id
            }
        }
    }

    override suspend fun onRegister(ownerUserId: String?, name: String, statuses: List<Status>):
        String {
        val id = uuid()
        val activity =
            Activity(id, ownerUserId, name, Icon.default, statuses, observerUserIds = emptyList())
        _activities.value += activity
        return id
    }

    override suspend fun onUnregister(userId: String, activityId: String) {
        _activities.value -= activities.first().first {
            it.id == activityId
        }
    }
}