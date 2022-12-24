package com.jeanbarrossilva.ongoing.core.registry.inmemory

import com.jeanbarrossilva.ongoing.core.extensions.replaceBy
import com.jeanbarrossilva.ongoing.core.registry.ActivityRecorder
import com.jeanbarrossilva.ongoing.core.registry.OnStatusChangeListener
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status

class InMemoryActivityRecorder(
    override val registry: InMemoryActivityRegistry
): ActivityRecorder() {
    private val onStatusChangeListeners = mutableListOf<OnStatusChangeListener>()

    override suspend fun onName(activityId: String, name: String) {
        replace(activityId) {
            copy(name = name)
        }
    }

    override suspend fun onIcon(activityId: String, icon: Icon) {
        replace(activityId) {
            copy(icon = icon)
        }
    }

    override suspend fun onStatus(activityId: String, status: Status) {
        replace(activityId) {
            this.status = status
            notifyStatusChange(this)
            this
        }
    }

    override suspend fun doOnStatusChange(listener: OnStatusChangeListener) {
        onStatusChangeListeners.add(listener)
    }

    private fun replace(activityId: String, replacement: Activity.() -> Activity) {
        registry.activities.replaceBy(replacement) {
            it.id == activityId
        }
    }

    private fun notifyStatusChange(activity: Activity) {
        onStatusChangeListeners.forEach {
            it.onStatusChange(activity)
        }
    }
}