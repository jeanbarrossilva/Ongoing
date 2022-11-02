package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityEntity
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusDao
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal fun ActivityEntity.toActivity(statusDao: StatusDao): Flow<Activity> {
    return statusDao.getStatusesByActivityId(id).map { it.map(StatusEntity::toStatus) }.map {
        toActivity(it)
    }
}

internal fun ActivityEntity.toActivity(statuses: List<Status>): Activity {
    return Activity("$id", ownerUserId, name, icon, statuses)
}