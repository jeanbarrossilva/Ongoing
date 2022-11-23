package com.jeanbarrossilva.ongoing.feature.activitydetails.observation.requester

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.os.bundleOf
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.observation.ActivityDetailsObservationRequester

internal class NavigateToNotificationsSettingsObservationRequester(
    override val next: ActivityDetailsObservationRequester?
): ActivityDetailsObservationRequester() {
    override fun request(
        activity: ActivityDetailsActivity,
        isObserving: Boolean,
        onAcceptance: () -> Unit
    ) {
        if (
            !activity.areNotificationsEnabled &&
            !activity.canAndShouldAskForPermission &&
            isObserving
        ) {
            navigateToNotificationsSettings(activity)
        } else {
            next?.request(activity, isObserving, onAcceptance)
        }
    }

    private fun navigateToNotificationsSettings(context: Context) {
        val extras = bundleOf(Settings.EXTRA_APP_PACKAGE to context.packageName)
        val intent =
            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply { putExtras(extras) }
        context.startActivity(intent)
    }
}