package com.jeanbarrossilva.ongoing.feature.activitydetails.observation.requester

import android.Manifest
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.arrayOfNotNull
import com.jeanbarrossilva.ongoing.feature.activitydetails.observation.ActivityDetailsObservationRequester

internal class AskForPermissionToSendNotificationsObservationRequester(
    override val next: ActivityDetailsObservationRequester?
): ActivityDetailsObservationRequester() {
    override fun request(
        activity: ActivityDetailsActivity,
        isObserving: Boolean,
        onAcceptance: () -> Unit
    ) {
        if (
            !activity.areNotificationsEnabled &&
            activity.canAndShouldAskForPermission &&
            isObserving
        ) {
            askForPermissionToSendNotifications(activity)
        } else {
            next?.request(activity, isObserving, onAcceptance)
        }
    }

    private fun askForPermissionToSendNotifications(activity: ActivityDetailsActivity) {
        val permission =
            if (isInAndroidTiramisuOrHigher) Manifest.permission.POST_NOTIFICATIONS else null
        val permissions = arrayOfNotNull(permission)
        activity.requestPermissions(permissions, ActivityDetailsActivity.permissionRequestCode)
    }
}