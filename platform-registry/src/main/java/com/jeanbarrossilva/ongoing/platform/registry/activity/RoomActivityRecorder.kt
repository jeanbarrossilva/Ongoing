package com.jeanbarrossilva.ongoing.platform.registry.activity

import com.jeanbarrossilva.ongoing.core.registry.OnStatusChangeListener
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.registry.observation.Change
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.platform.registry.extensions.toStatus
import com.jeanbarrossilva.ongoing.platform.registry.observer.RoomActivityObserver
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusDao
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusEntity
import kotlinx.coroutines.flow.first

class RoomActivityRecorder internal constructor(
    private val sessionManager: SessionManager,
    private val activityDao: ActivityDao,
    private val statusDao: StatusDao,
    private val observer: RoomActivityObserver
): Activity.Recorder() {
    private val onStatusChangeListeners = mutableListOf<OnStatusChangeListener>()

    private val currentUserId
        get() = sessionManager.session<Session.SignedIn>()?.userId

    override suspend fun ownerUserId(id: String, ownerUserId: String?) {
        activityDao.updateOwnerUserId(id, ownerUserId)
    }

    override suspend fun name(id: String, name: String) {
        val currentName = activityDao.selectName(id)
        activityDao.updateName(id, name)
        observer.notify(currentUserId, id, Change.Name(currentName, name))
    }

    override suspend fun icon(id: String, icon: Icon) {
        activityDao.updateIcon(id, icon)
    }

    override suspend fun status(id: String, status: Status) {
        val idAsLong = id.toLong()
        val currentStatus =
            statusDao.getStatusesByActivityId(idAsLong).first().lastOrNull()?.toStatus()
        val isNotRepeated = currentStatus != status
        if (isNotRepeated) {
            record(idAsLong, status)
            observer.notify(currentUserId, id, Change.Status(currentStatus, status))
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