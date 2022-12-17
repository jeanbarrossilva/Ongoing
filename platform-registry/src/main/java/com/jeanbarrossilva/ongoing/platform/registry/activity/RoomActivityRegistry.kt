package com.jeanbarrossilva.ongoing.platform.registry.activity

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.platform.registry.extensions.toActivity
import com.jeanbarrossilva.ongoing.platform.registry.observer.ObserverDao
import com.jeanbarrossilva.ongoing.platform.registry.observer.RoomActivityObserver
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RoomActivityRegistry(
    coroutineScope: CoroutineScope,
    session: Session,
    private val ownershipManager: RoomActivityOwnershipManager,
    private val activityDao: ActivityDao,
    private val statusDao: StatusDao,
    private val observerDao: ObserverDao
) : ActivityRegistry() {
    override val observer = RoomActivityObserver(activityDao, observerDao)
    override val recorder = RoomActivityRecorder(session, activityDao, statusDao, observer)

    init {
        coroutineScope.launch {
            ownershipManager.start(this@RoomActivityRegistry)
        }
    }

    override suspend fun getActivities(): List<Activity> {
        return activityDao.selectAll().map {
            it.toActivity(statusDao, observerDao).first()
        }
    }

    override suspend fun getActivityById(id: String): Activity? {
        return activityDao.selectById(id)?.toActivity(statusDao, observerDao)?.first()
    }

    override suspend fun onRegister(ownerUserId: String?, name: String, statuses: List<Status>):
        String {
        assert(name.isNotBlank())
        val entity = ActivityEntity(id = 0, ownerUserId, name, Icon.default)
        val generatedActivityId = activityDao.insert(entity).toString()
        statuses.forEach { recorder.status(generatedActivityId, it) }
        return generatedActivityId
    }

    override suspend fun onUnregister(userId: String, activityId: String) {
        val activityIdAsLong = activityId.toLong()
        statusDao.deleteByActivityId(activityIdAsLong)
        activityDao.delete(activityIdAsLong)
    }

    override suspend fun clear(userId: String) {
        observer.clear()
        with(activityDao) {
            selectByOwnerUserId(userId).forEach {
                statusDao.deleteByActivityId(it.id)
                delete(it.id)
            }
        }
    }
}