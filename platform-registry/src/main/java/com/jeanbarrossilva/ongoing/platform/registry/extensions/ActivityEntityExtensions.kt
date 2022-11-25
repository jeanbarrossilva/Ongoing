package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityEntity
import com.jeanbarrossilva.ongoing.platform.registry.observer.ObserverDao
import com.jeanbarrossilva.ongoing.platform.registry.observer.ObserverEntity
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusDao
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

internal fun ActivityEntity.toActivity(statusDao: StatusDao, observerDao: ObserverDao):
    Flow<Activity> {
    val statusesFlow = statusDao.getStatusesByActivityId(id).map { it.map(StatusEntity::toStatus) }
    val observerUserIdsFlow =
        observerDao.selectByActivityId(id).map { it.map(ObserverEntity::userId) }
    return combine(statusesFlow, observerUserIdsFlow) { statuses, observerUserIds ->
        toActivity(statuses, observerUserIds)
    }
}

internal fun ActivityEntity.toActivity(
    statuses: List<Status> = emptyList(),
    observerUserIds: List<String> = emptyList()
): Activity {
    return Activity("$id", ownerUserId, name, icon, statuses, observerUserIds)
}