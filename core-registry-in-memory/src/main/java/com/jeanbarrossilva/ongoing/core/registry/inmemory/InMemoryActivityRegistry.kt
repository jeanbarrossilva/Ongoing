package com.jeanbarrossilva.ongoing.core.registry.inmemory

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.OnStatusChangeListener
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.registry.inmemory.extensions.flatten
import com.jeanbarrossilva.ongoing.core.registry.inmemory.extensions.getValue
import com.jeanbarrossilva.ongoing.core.registry.inmemory.extensions.setValue
import com.jeanbarrossilva.ongoing.core.registry.inmemory.extensions.uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach

class InMemoryActivityRegistry: ActivityRegistry {
    private val activitiesFlow = MutableStateFlow(emptyList<Activity>())
    private var activities by activitiesFlow

    override val recorder = InMemoryActivityRecorder()

    inner class InMemoryActivityRecorder internal constructor(): Activity.Recorder() {
        private val onStatusChangeListeners = mutableListOf<OnStatusChangeListener>()

        override suspend fun name(id: String, name: String) {
            replace(id) {
                copy(name = name)
            }
        }

        override suspend fun icon(id: String, icon: Icon) {
            replace(id) {
                copy(icon = icon)
            }
        }

        override suspend fun currentStatus(id: String, currentStatus: Status) {
            replace(id) { copy(currentStatus = currentStatus) }
            requireActivityById(id).first().let { activity ->
                onStatusChangeListeners.forEach { listener ->
                    listener.onStatusChange(activity)
                }
            }
        }

        override suspend fun doOnStatusChange(listener: OnStatusChangeListener) {
            onStatusChangeListeners.add(listener)
        }

        private suspend fun replace(id: String, update: Activity.() -> Activity) {
            val currentActivity = requireActivityById(id).first()
            val updatedActivity = currentActivity.update()
            val index = activities.indexOf(currentActivity)
            unregister(currentActivity.id)
            activities = activities.toMutableList().apply { add(index, updatedActivity) }
        }
    }

    override suspend fun getActivities(): Flow<List<Activity>> {
        return activitiesFlow
    }

    override suspend fun getActivityById(id: String): Flow<Activity?> {
        return activitiesFlow.flatten().filter {
            it.id == id
        }
    }

    override suspend fun register(ownerUserId: String, name: String, statuses: List<Status>): String {
        val id = uuid()
        val activity =
            Activity(id, ownerUserId, name, Icon.OTHER, statuses, currentStatus = Status.TO_DO)
        activities = activities + activity
        return id
    }

    override suspend fun unregister(id: String) {
        activities = activities - requireActivityById(id).first()
    }

    override suspend fun clear() {
        activities = emptyList()
    }

    private suspend fun requireActivityById(id: String): Flow<Activity> {
        return getActivityById(id)
            .onEach { if (it == null) throw IllegalArgumentException() }
            .filterNotNull()
    }
}