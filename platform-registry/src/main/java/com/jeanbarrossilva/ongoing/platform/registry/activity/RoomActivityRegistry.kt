package com.jeanbarrossilva.ongoing.platform.registry.activity

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.platform.registry.extensions.toActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomActivityRegistry(private val dao: ActivityDao) : ActivityRegistry {
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

    override suspend fun register(
        ownerUserId: String,
        name: String,
        statuses: List<Status>
    ): String {
        val activity = ActivityEntity(id = 0, ownerUserId, name, Icon.OTHER, Status.TO_DO)
        return dao.insert(activity).toString()
    }

    override suspend fun unregister(id: String) {
        dao.delete(id)
    }

    override suspend fun clear() {
        dao.deleteAll()
    }
}