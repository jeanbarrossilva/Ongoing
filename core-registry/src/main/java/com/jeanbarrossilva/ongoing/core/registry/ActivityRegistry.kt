package com.jeanbarrossilva.ongoing.core.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import kotlinx.coroutines.flow.Flow

abstract class ActivityRegistry {
    abstract val recorder: Activity.Recorder
    abstract val observer: Activity.Observer
    abstract val activities: Flow<List<Activity>>

    abstract suspend fun getActivityById(id: String): Flow<Activity?>

    abstract suspend fun register(
        ownerUserId: String,
        name: String,
        statuses: List<Status> = Status.default
    ): String

    abstract suspend fun clear(userId: String)

    suspend fun unregister(userId: String, id: String) {
        ActivityRegistryUnregistrationValidatorFactory.create(this).validate(userId, id)
        onUnregister(userId, id)
    }

    protected abstract suspend fun onUnregister(userId: String, activityId: String)

    protected fun createActivity(
        ownerUserId: String,
        id: String,
        name: String,
        statuses: List<Status>
    ): Activity {
        return Companion.createActivity(ownerUserId, id, name, statuses)
    }

    companion object {
        internal fun createActivity(
            ownerUserId: String,
            id: String,
            name: String,
            statuses: List<Status> = Status.default
        ): Activity {
            return Activity(
                id,
                ownerUserId,
                name,
                Icon.OTHER,
                statuses,
                observerUserIds = emptyList()
            )
        }
    }
}