package com.jeanbarrossilva.ongoing.feature.activityediting

import android.os.Parcelable
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import kotlinx.parcelize.Parcelize

sealed class ActivityEditingMode : Parcelable {
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
            val currentUserId = getCurrentUserId(sessionManager)
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
            val currentUserId = getCurrentUserId(sessionManager)
            activityRegistry.recorder.name(currentUserId, activity.id, props.name)
            activityRegistry.recorder.status(
                currentUserId,
                activity.id,
                requireNotNull(props.currentStatus).toStatus()
            )
        }

        companion object {
            internal val sample = Modification(ContextualActivity.sample)
        }
    }

    internal abstract fun hasChanges(props: ActivityEditingProps): Boolean

    internal abstract suspend fun save(
        sessionManager: SessionManager,
        activityRegistry: ActivityRegistry,
        props: ActivityEditingProps
    )

    protected fun getCurrentUserId(sessionManager: SessionManager): String {
        val session = sessionManager.session() as? Session.SignedIn
            ?: throw IllegalStateException("Cannot edit an activity while signed out.")
        return session.userId
    }
}