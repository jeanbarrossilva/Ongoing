package com.jeanbarrossilva.ongoing.feature.activitydetails.observation

import android.content.Context
import android.content.Intent
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.domain.observation.ContextualChange
import com.jeanbarrossilva.ongoing.context.registry.extensions.contextualize
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Change
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.core.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.R
import com.jeanbarrossilva.ongoing.feature.activitydetails.bridge.ActivityDetailsBridge
import com.jeanbarrossilva.ongoing.feature.activitydetails.bridge.ActivityDetailsBridgeCrossing
import com.jeanbarrossilva.ongoing.platform.extensions.createNotificationChannel
import com.jeanbarrossilva.ongoing.platform.extensions.notify
import java.lang.ref.WeakReference

internal class ActivityDetailsObservation(
    private val contextRef: WeakReference<Context>,
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val activityRegistry: ActivityRegistry,
    private val boundary: ActivityDetailsBoundary,
    private val fetcher: ContextualActivitiesFetcher
): Observation {
    private val context
        get() = contextRef.get()

    constructor(
        context: Context,
        sessionManager: SessionManager,
        userRepository: UserRepository,
        activityRegistry: ActivityRegistry,
        boundary: ActivityDetailsBoundary,
        fetcher: ContextualActivitiesFetcher
    ): this(
        WeakReference(context),
        sessionManager,
        userRepository,
        activityRegistry,
        boundary,
        fetcher
    )

    override suspend fun onChange(changerUserId: String?, activityId: String, change: Change) {
        val isChangeMadeBySomeoneElse =
            sessionManager.session<Session.SignedIn>()?.userId != changerUserId
        if (isChangeMadeBySomeoneElse) {
            val contextualChange = change.contextualize()
            activityRegistry
                .getActivityById(activityId)
                ?.toContextualActivity(sessionManager, userRepository)
                ?.let { notify(contextualChange, it) }
        }
    }

    private fun notify(change: ContextualChange, activity: ContextualActivity) {
        context?.run {
            createNotificationChannel(
                CHANNEL_ID,
                R.string.feature_activity_details_notification_channel_name
            )
            notify(
                CHANNEL_ID,
                smallIconRes = R.drawable.ic_glyph,
                title = getString(R.string.feature_activity_details_notification_title),
                text = change.getMessage(this, activity),
                getIntent(activity)
            )
        }
    }

    private fun getIntent(activity: ContextualActivity): Intent {
        ActivityDetailsBridge.cross(
            context,
            sessionManager,
            activityRegistry,
            this,
            fetcher,
            boundary,
            ActivityDetailsBridgeCrossing.RetentionOnly
        )
        return ActivityDetailsActivity.getIntent(context, activity.id)
    }

    companion object {
        private const val CHANNEL_ID = "activity_observation"
    }
}