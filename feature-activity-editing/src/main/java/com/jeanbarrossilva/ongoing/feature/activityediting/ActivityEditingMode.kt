package com.jeanbarrossilva.ongoing.feature.activityediting

import android.os.Parcelable
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import kotlinx.parcelize.Parcelize

sealed class ActivityEditingMode : Parcelable {
    internal abstract fun hasChanges(props: ActivityEditingProps): Boolean

    internal abstract suspend fun save(
        activityRegistry: ActivityRegistry,
        props: ActivityEditingProps
    )

    @Parcelize
    object Addition: ActivityEditingMode() {
        override fun hasChanges(props: ActivityEditingProps): Boolean {
            return props.name.isNotBlank() || props.currentStatus != null
        }

        override suspend fun save(activityRegistry: ActivityRegistry, props: ActivityEditingProps) {
            activityRegistry.register(props.name)
        }
    }

    @Parcelize
    class Modification(val activity: ContextualActivity): ActivityEditingMode() {
        override fun hasChanges(props: ActivityEditingProps): Boolean {
            return props.name != activity.name || props.currentStatus != activity.status
        }

        override suspend fun save(activityRegistry: ActivityRegistry, props: ActivityEditingProps) {
            activityRegistry.recorder.name(activity.id, props.name)
            activityRegistry.recorder.status(
                 activity.id,
                requireNotNull(props.currentStatus).toStatus()
            )
        }

        companion object {
            internal val sample = Modification(ContextualActivity.sample)
        }
    }
}