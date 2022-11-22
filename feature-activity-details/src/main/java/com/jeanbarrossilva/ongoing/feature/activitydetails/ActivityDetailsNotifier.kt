package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.observation.ContextualChange
import com.jeanbarrossilva.ongoing.platform.extensions.createNotificationChannel
import com.jeanbarrossilva.ongoing.platform.extensions.notify

internal object ActivityDetailsNotifier {
    private const val CHANNEL_ID = "activity_observation"

    fun onChange(context: Context, change: ContextualChange, activity: ContextualActivity) {
        context.createNotificationChannel(
            CHANNEL_ID,
            R.string.feature_activity_details_notification_channel_name
        )
        context.notify(
            CHANNEL_ID,
            smallIconRes =
                com.jeanbarrossilva.ongoing.platform.designsystem.R.mipmap.ic_launcher_round,
            title = context.getString(R.string.feature_activity_details_notification_title),
            text = change.getMessage(context, activity),
            ActivityDetailsActivity.getIntent(context, activity.id)
        )
    }
}