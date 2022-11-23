package com.jeanbarrossilva.ongoing.feature.activitydetails.observation

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.observation.ContextualChange
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.extensions.contextualize
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Change
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.R
import com.jeanbarrossilva.ongoing.platform.extensions.createNotificationChannel
import com.jeanbarrossilva.ongoing.platform.extensions.notify
import java.lang.ref.WeakReference
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

internal class ActivityDetailsObservation(
    private val contextRef: WeakReference<Context>,
    private val session: Session,
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry
): Observation {
    private val context
        get() = contextRef.get()

    constructor(
        context: Context,
        session: Session,
        userRepository: UserRepository,
        activityRegistry: ActivityRegistry
    ): this(WeakReference(context), session, userRepository, activityRegistry)

    override suspend fun onChange(change: Change, activityId: String) {
        val contextualChange = change.contextualize()
        val activity = activityRegistry
            .getActivityById(activityId)
            .filterNotNull()
            .first()
            .toContextualActivity(session, userRepository)
        notify(contextualChange, activity)
    }

    private fun notify(change: ContextualChange, activity: ContextualActivity) {
        context?.run {
            createNotificationChannel(
                CHANNEL_ID,
                R.string.feature_activity_details_notification_channel_name
            )
            notify(
                CHANNEL_ID,
                smallIconRes =
                    com.jeanbarrossilva.ongoing.platform.designsystem.R.mipmap.ic_launcher_round,
                title = getString(R.string.feature_activity_details_notification_title),
                text = change.getMessage(this, activity),
                ActivityDetailsActivity.getIntent(this, activity.id)
            )
        }
    }

    companion object {
        private const val CHANNEL_ID = "activity_observation"
    }
}