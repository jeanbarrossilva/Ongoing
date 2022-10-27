package com.jeanbarrossilva.ongoing.platform.registry.activity

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.platform.registry.authorization.CurrentUserIdProvider
import com.jeanbarrossilva.ongoing.platform.registry.authorization.UnauthorizedException
import com.jeanbarrossilva.ongoing.platform.registry.extensions.toActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class RoomActivityRegistry(
    private val dao: ActivityDao,
    private val currentUserIdProvider: CurrentUserIdProvider
) : ActivityRegistry {
    override val recorder = RoomActivityRecorder(dao)

    override suspend fun getActivities(): Flow<List<Activity>> {
        return dao.selectAll().map {
            it.map(ActivityEntity::toActivity)
        }
    }

    override suspend fun getActivityById(id: String): Flow<Activity?> {
        return dao.selectById(id).map {
            it?.toActivity()
        }
    }

    override suspend fun register(name: String, statuses: List<Status>): String {
        assert(name.isNotBlank())
        val activity = ActivityEntity(id = 0, currentUserId(), name, Icon.OTHER, Status.TO_DO)
        return dao.insert(activity).toString()
    }

    override suspend fun unregister(id: String) {
        val isActivityExistent = dao.selectById(id).first() != null
        val isActivityOwner = dao.selectOwnerUserId(id) == currentUserId()
        when {
            isActivityExistent && isActivityOwner -> dao.delete(id)
            isActivityExistent && !isActivityOwner -> throw UnauthorizedException(id)
            else -> throw IllegalArgumentException("Activity $id does not exist.")
        }
    }

    override suspend fun clear() {
        dao.deleteAll()
    }

    private suspend fun currentUserId(): String {
        return currentUserIdProvider.provide()
    }
}