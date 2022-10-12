package com.jeanbarrossilva.ongoing.core.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import kotlinx.coroutines.flow.Flow

interface ActivityRegistry {
    suspend fun getActivities(): Flow<List<Activity>>

    suspend fun getActivityById(id: String): Activity?

    suspend fun register(ownerId: String, name: String, statuses: List<Status> = Status.all): String

    suspend fun setName(id: String, name: String)

    suspend fun setIcon(id: String, icon: Icon)

    suspend fun setCurrentStatus(id: String, status: Status)

    suspend fun unregister(id: String)

    suspend fun clear()
}