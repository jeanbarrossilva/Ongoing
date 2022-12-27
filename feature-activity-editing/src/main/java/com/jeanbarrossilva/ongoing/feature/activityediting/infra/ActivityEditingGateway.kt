package com.jeanbarrossilva.ongoing.feature.activityediting.infra

import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingStatus
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList
import kotlinx.coroutines.flow.Flow

internal interface ActivityEditingGateway {
    suspend fun getActivity(): Flow<Loadable<EditingActivity>>

    suspend fun isChanged(activity: EditingActivity): Flow<Loadable<Boolean>>

    suspend fun isValid(activity: EditingActivity): Flow<Loadable<Boolean>>

    suspend fun getAvailableStatuses(): Flow<Loadable<SerializableList<EditingStatus>>>

    suspend fun edit(activity: EditingActivity)
}