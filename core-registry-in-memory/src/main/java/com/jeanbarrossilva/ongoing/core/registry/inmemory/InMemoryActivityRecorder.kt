package com.jeanbarrossilva.ongoing.core.registry.inmemory

import com.jeanbarrossilva.ongoing.core.extensions.replaceBy
import com.jeanbarrossilva.ongoing.core.registry.OnStatusChangeListener
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status

class InMemoryActivityRecorder internal constructor(
    private val activityRegistry: InMemoryActivityRegistry
): Activity.Recorder() {
    private val onStatusChangeListeners = mutableListOf<OnStatusChangeListener>()

    override suspend fun ownerUserId(id: String, ownerUserId: String?) {
        replace(id) {
            copy(ownerUserId = ownerUserId)
        }
    }

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

    override suspend fun status(id: String, status: Status) {
        replace(id) {
            this.status = status
            notifyStatusChange(this)
            this
        }
    }

    override suspend fun doOnStatusChange(listener: OnStatusChangeListener) {
        onStatusChangeListeners.add(listener)
    }

    private fun replace(activityId: String, replacement: Activity.() -> Activity) {
        activityRegistry.activities.replaceBy(replacement) {
            it.id == activityId
        }
    }

    private fun notifyStatusChange(activity: Activity) {
        onStatusChangeListeners.forEach {
            it.onStatusChange(activity)
        }
    }
}