package com.jeanbarrossilva.ongoing.feature.activityediting

import android.os.Parcelable
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualStatus
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import kotlinx.parcelize.Parcelize

@Parcelize
internal sealed class ActivityEditingMode : Parcelable {
    abstract suspend fun save(
        activityRegistry: ActivityRegistry,
        name: String,
        currentStatus: ContextualStatus
    )

    object Addition: ActivityEditingMode() {
        override suspend fun save(
            activityRegistry: ActivityRegistry,
            name: String,
            currentStatus: ContextualStatus
        ) {
            activityRegistry.register(name)
        }
    }

    class Modification(val activity: ContextualActivity): ActivityEditingMode() {
        override suspend fun save(
            activityRegistry: ActivityRegistry,
            name: String,
            currentStatus: ContextualStatus
        ) {
            activityRegistry.recorder.name(activity.id, name)
            activityRegistry.recorder.currentStatus(activity.id, currentStatus.toStatus())
        }
    }
}