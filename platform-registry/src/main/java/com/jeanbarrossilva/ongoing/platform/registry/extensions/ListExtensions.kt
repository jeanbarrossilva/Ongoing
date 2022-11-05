package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityEntity
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusDao
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusEntity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

@OptIn(FlowPreview::class)
internal fun List<ActivityEntity>.mapToActivity(statusDao: StatusDao): Flow<List<Activity>> {
    return map { statusDao.getStatusesByActivityId(it.id) }
        .asFlow()
        .flattenConcat()
        .onStart { map { activity -> activity.toActivity(statuses = emptyList()) } }
        .map { statusEntities -> statusEntities.map(StatusEntity::toStatus) }
        .map { statuses -> map { activity -> activity.toActivity(statuses) } }
}