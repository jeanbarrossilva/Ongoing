package com.jeanbarrossilva.ongoing.feature.activityediting.infra.inmemory

import android.content.Context
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.NonexistentActivityException
import com.jeanbarrossilva.ongoing.core.registry.activity.status.Status
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingStatus
import com.jeanbarrossilva.ongoing.feature.activityediting.extensions.toEditingActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.extensions.toStatus
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable

internal sealed interface EditingMode {
    class Addition(private val activityRegistry: ActivityRegistry): EditingMode {
        override suspend fun getActivity(context: Context): Loadable<EditingActivity?> {
            return Loadable.Loaded(EditingActivity(name = "", EditingStatus.toDo))
        }

        override suspend fun save(userId: String, activity: EditingActivity) {
            val status = activity.status.toStatus()
            val statuses = listOf(status)
            activityRegistry.register(userId, activity.name, statuses)
        }
    }

    class Modification(
        private val activityRegistry: ActivityRegistry,
        private val activityId: String
    ): EditingMode {
        override suspend fun getActivity(context: Context): Loadable<EditingActivity?> {
            return activityRegistry
                .getActivityById(activityId)
                ?.toEditingActivity(context)
                ?.let { Loadable.Loaded(it) }
                ?: Loadable.Failed(NonexistentActivityException(activityId))
        }

        override suspend fun save(userId: String, activity: EditingActivity) {
            val status = Status.valueOf(activity.status.id)
            activityRegistry.recorder.name(userId, activityId, activity.name)
            activityRegistry.recorder.status(userId, activityId, status)
        }
    }

    suspend fun getActivity(context: Context): Loadable<EditingActivity?>

    suspend fun save(userId: String, activity: EditingActivity)
}