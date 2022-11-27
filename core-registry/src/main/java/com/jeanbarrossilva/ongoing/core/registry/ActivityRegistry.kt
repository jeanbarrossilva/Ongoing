package com.jeanbarrossilva.ongoing.core.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

abstract class ActivityRegistry {
    abstract val recorder: Activity.Recorder
    abstract val observer: Activity.Observer
    abstract val activities: Flow<List<Activity>>

    abstract suspend fun getActivityById(id: String): Flow<Activity?>

    abstract suspend fun register(
        ownerUserId: String?,
        name: String,
        statuses: List<Status> = Status.default
    ): String

    suspend fun unregister(userId: String, id: String) {
        ActivityRegistryUnregistrationValidatorFactory.create(this).validate(userId, id)
        onUnregister(userId, id)
    }

    open suspend fun clear(userId: String) {
        activities.first().forEach {
            unregister(userId, it.id)
        }
    }

    protected abstract suspend fun onUnregister(userId: String, activityId: String)
}