package com.jeanbarrossilva.ongoing.platform.registry.activity

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.platform.registry.extensions.flatten
import com.jeanbarrossilva.ongoing.platform.registry.extensions.mapToActivity
import com.jeanbarrossilva.ongoing.platform.registry.extensions.toActivity
import com.jeanbarrossilva.ongoing.platform.registry.observer.ObserverDao
import com.jeanbarrossilva.ongoing.platform.registry.observer.RoomActivityObserver
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
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

    @OptIn(FlowPreview::class)
    override val activities =
        activityDao.selectAll().flatMapConcat { it.mapToActivity(statusDao, observerDao) }

    init {
        coroutineScope.launch {
            ownershipManager.start(this@RoomActivityRegistry)
        }
    }

    override suspend fun getActivityById(id: String): Flow<Activity?> {
        return activityDao.selectById(id).map { it?.toActivity(statusDao, observerDao) }.flatten()
    }

    override suspend fun register(ownerUserId: String?, name: String, statuses: List<Status>):
        String {
        assert(name.isNotBlank())
        val entity = ActivityEntity(id = 0, ownerUserId, name, Icon.default)
        val generatedActivityId = activityDao.insert(entity).toString()
        recorder.status(generatedActivityId, Status.TO_DO)
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