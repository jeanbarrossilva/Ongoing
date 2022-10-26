package com.jeanbarrossilva.ongoing.platform.registry.activity

import com.jeanbarrossilva.ongoing.core.registry.OnStatusChangeListener
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status

class RoomActivityRecorder internal constructor(private val dao: ActivityDao): Activity.Recorder() {
    private val onStatusChangeListeners = mutableListOf<OnStatusChangeListener>()

    override suspend fun name(id: String, name: String) {
        dao.updateName(id, name)
    }

    override suspend fun icon(id: String, icon: Icon) {
        dao.updateIcon(id, icon)
    }

    override suspend fun currentStatus(id: String, currentStatus: Status) {
        dao.updateCurrentStatus(id, currentStatus)
    }

    override suspend fun doOnStatusChange(listener: OnStatusChangeListener) {
        onStatusChangeListeners.add(listener)
    }
}