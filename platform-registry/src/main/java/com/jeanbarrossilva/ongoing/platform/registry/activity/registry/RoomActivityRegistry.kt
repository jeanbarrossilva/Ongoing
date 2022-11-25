package com.jeanbarrossilva.ongoing.platform.registry.activity.registry

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityDao
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityEntity
import com.jeanbarrossilva.ongoing.platform.registry.activity.RoomActivityOwnershipManager
import com.jeanbarrossilva.ongoing.platform.registry.activity.RoomActivityRecorder
import com.jeanbarrossilva.ongoing.platform.registry.extensions.flatten
import com.jeanbarrossilva.ongoing.platform.registry.extensions.mapToActivity
import com.jeanbarrossilva.ongoing.platform.registry.extensions.toActivity
import com.jeanbarrossilva.ongoing.platform.registry.observer.ObserverDao
import com.jeanbarrossilva.ongoing.platform.registry.observer.RoomActivityObserver
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class RoomActivityRegistry(
    coroutineScope: CoroutineScope,
    private val session: Session,
    private val ownershipManager: RoomActivityOwnershipManager,
    private val activityDao: ActivityDao,
    private val statusDao: StatusDao,
    private val observerDao: ObserverDao
) : ActivityRegistry {
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

    override suspend fun register(name: String, statuses: List<Status>): String {
        assert(name.isNotBlank())
        val activity = ActivityEntity(id = 0, currentUserId(), name, Icon.OTHER)
        val generatedId = activityDao.insert(activity).toString()
        recorder.status(generatedId, Status.TO_DO)
        return generatedId
    }

    override suspend fun unregister(id: String) {
        val isActivityExistent = activityDao.selectById(id).first() != null
        val isActivityOwner = activityDao.selectOwnerUserId(id) == currentUserId()
        val result = UnregisteringResult.of(isActivityExistent, isActivityOwner)
        unregister(id, result)
    }

    override suspend fun clear() {
        statusDao.deleteAll()
        observer.clear()
        activityDao.deleteAll()
    }

    private suspend fun currentUserId(): String? {
        return session.getUser().first()?.id
    }

    private suspend fun unregister(id: String, result: UnregisteringResult) {
        when (result) {
            UnregisteringResult.Allowed -> unregisterActivityAlongsideItsStatuses(id)
            UnregisteringResult.Denied -> throw UnregisteringResult.Denied.Exception(id)
            UnregisteringResult.Nonexistent -> throw UnregisteringResult.Nonexistent.Exception(id)
        }
    }

    private suspend fun unregisterActivityAlongsideItsStatuses(id: String) {
        statusDao.deleteByActivityId(id.toLong())
        activityDao.delete(id)
    }
}