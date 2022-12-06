package com.jeanbarrossilva.ongoing.core.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import kotlinx.coroutines.flow.first

abstract class ActivityRegistry {
    abstract val recorder: Activity.Recorder
    abstract val observer: Activity.Observer

    abstract suspend fun getActivities(): List<Activity>

    abstract suspend fun getActivityById(id: String): Activity?

    suspend fun register(
        ownerUserId: String?,
        name: String,
        statuses: List<Status> = Status.default,
    ): String {
        name.ifBlank {
            throw IllegalArgumentException("Cannot register an activity with a blank name.")
        }
        return onRegister(ownerUserId, name, statuses)
    }

    suspend fun unregister(userId: String, id: String) {
        ActivityRegistryUnregistrationValidatorFactory.create(this).validate(userId, id)
        onUnregister(userId, id)
    }

    open suspend fun clear(userId: String) {
        getActivities().forEach {
            unregister(userId, it.id)
        }
    }

    protected abstract suspend fun onRegister(
        ownerUserId: String?,
        name: String,
        statuses: List<Status>
    ): String

    protected abstract suspend fun onUnregister(userId: String, activityId: String)
}