package com.jeanbarrossilva.ongoing.feature.activityediting

import android.os.Parcelable
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import kotlinx.parcelize.Parcelize

sealed class ActivityEditingMode : Parcelable {
    internal abstract fun hasChanges(props: ActivityEditingProps): Boolean

    internal abstract suspend fun save(
        sessionManager: SessionManager,
        activityRegistry: ActivityRegistry,
        props: ActivityEditingProps
    )

    @Parcelize
    object Addition: ActivityEditingMode() {
        override fun hasChanges(props: ActivityEditingProps): Boolean {
            return props.name.isNotBlank() || props.currentStatus != null
        }

        override suspend fun save(
            sessionManager: SessionManager,
            activityRegistry: ActivityRegistry,
            props: ActivityEditingProps
        ) {
            val currentUserId = sessionManager.session<Session.SignedIn>()?.userId
            activityRegistry.register(currentUserId, props.name)
        }
    }

    @Parcelize
    class Modification(val activity: ContextualActivity): ActivityEditingMode() {
        override fun hasChanges(props: ActivityEditingProps): Boolean {
            return props.name != activity.name || props.currentStatus != activity.status
        }

        override suspend fun save(
            sessionManager: SessionManager,
            activityRegistry: ActivityRegistry,
            props: ActivityEditingProps
        ) {
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