package com.jeanbarrossilva.ongoing.platform.registry.activity

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.OnStatusChangeListener
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.status.Status
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
    override val registry: ActivityRegistry,
    private val activityDao: ActivityDao,
    private val statusDao: StatusDao,
    private val observer: RoomActivityObserver
): Activity.Recorder() {
    private val onStatusChangeListeners = mutableListOf<OnStatusChangeListener>()

    private val currentUserId
        get() = sessionManager.session<Session.SignedIn>()?.userId

    override suspend fun onName(activityId: String, name: String) {
        val currentName = activityDao.selectName(activityId)
        activityDao.updateName(activityId, name)
        observer.notify(currentUserId, activityId, Change.Name(currentName, name))
    }

    override suspend fun onIcon(activityId: String, icon: Icon) {
        activityDao.updateIcon(activityId, icon)
    }

    override suspend fun onStatus(activityId: String, status: Status) {
        val idAsLong = activityId.toLong()
        val currentStatus =
            statusDao.getStatusesByActivityId(idAsLong).first().lastOrNull()?.toStatus()
        val isNotRepeated = currentStatus != status
        if (isNotRepeated) {
            record(idAsLong, status)
            observer.notify(currentUserId, activityId, Change.Status(currentStatus, status))
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