package com.jeanbarrossilva.ongoing.feature.activities

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList
import kotlinx.coroutines.flow.Flow

interface ActivitiesGateway {
    suspend fun getCurrentOwner(): Flow<Loadable<ActivitiesOwner?>>

    suspend fun getActivities(): Flow<Loadable<SerializableList<ContextualActivity>>>

    suspend fun fetch()

    suspend fun unregister(activityIds: List<String>)
}