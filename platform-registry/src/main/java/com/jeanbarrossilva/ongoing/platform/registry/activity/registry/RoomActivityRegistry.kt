package com.jeanbarrossilva.ongoing.platform.registry.activity.registry

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityDao
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityEntity
import com.jeanbarrossilva.ongoing.platform.registry.activity.RoomActivityRecorder
import com.jeanbarrossilva.ongoing.platform.registry.authorization.CurrentUserIdProvider
import com.jeanbarrossilva.ongoing.platform.registry.extensions.flatten
import com.jeanbarrossilva.ongoing.platform.registry.extensions.mapToActivity
import com.jeanbarrossilva.ongoing.platform.registry.extensions.toActivity
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusDao
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class RoomActivityRegistry(
    private val activityDao: ActivityDao,
    private val statusDao: StatusDao,
    private val currentUserIdProvider: CurrentUserIdProvider
) : ActivityRegistry {
    override val recorder = RoomActivityRecorder(activityDao, statusDao)

    @OptIn(FlowPreview::class)
    override suspend fun getActivities(): Flow<List<Activity>> {
        return activityDao.selectAll().flatMapConcat {
            it.mapToActivity(statusDao)
        }
    }

    override suspend fun getActivityById(id: String): Flow<Activity?> {
        return activityDao.selectById(id).map { it?.toActivity(statusDao) }.flatten()
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
        activityDao.deleteAll()
    }

    private suspend fun currentUserId(): String {
        return currentUserIdProvider.provide()
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