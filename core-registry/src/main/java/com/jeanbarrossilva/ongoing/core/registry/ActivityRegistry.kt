package com.jeanbarrossilva.ongoing.core.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import kotlinx.coroutines.flow.Flow

interface ActivityRegistry {
    val recorder: Activity.Recorder

    suspend fun getActivities(): Flow<List<Activity>>

    suspend fun getActivityById(id: String): Flow<Activity?>

    suspend fun register(name: String, statuses: List<Status> = Status.values):
        String

    suspend fun unregister(id: String)

    suspend fun clear()
}