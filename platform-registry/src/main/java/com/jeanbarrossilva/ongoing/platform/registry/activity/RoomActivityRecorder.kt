package com.jeanbarrossilva.ongoing.platform.registry.activity

import com.jeanbarrossilva.ongoing.core.registry.OnStatusChangeListener
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.platform.registry.extensions.toStatus
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusDao
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusEntity
import kotlinx.coroutines.flow.first

class RoomActivityRecorder internal constructor(
    private val activityDao: ActivityDao,
    private val statusDao: StatusDao
): Activity.Recorder() {
    private val onStatusChangeListeners = mutableListOf<OnStatusChangeListener>()

    override suspend fun ownerUserId(id: String, ownerUserId: String?) {
        activityDao.updateOwnerUserId(id, ownerUserId)
    }

    override suspend fun name(id: String, name: String) {
        activityDao.updateName(id, name)
    }

    override suspend fun icon(id: String, icon: Icon) {
        activityDao.updateIcon(id, icon)
    }

    override suspend fun status(id: String, status: Status) {
        val idAsLong = id.toLong()
        val lastStatus =
            statusDao.getStatusesByActivityId(idAsLong).first().lastOrNull()?.toStatus()
        val isNotRepeated = lastStatus != status
        if (isNotRepeated) {
            record(idAsLong, status)
        }
    }

    override suspend fun doOnStatusChange(listener: OnStatusChangeListener) {
        onStatusChangeListeners.add(listener)
    }

    private suspend fun record(id: Long, status: Status) {
        val entity = StatusEntity(id = 0, id, status.name)
        statusDao.insert(entity)
    }
}