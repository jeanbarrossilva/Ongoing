package com.jeanbarrossilva.ongoing.feature.activitydetails.observation

import android.Manifest
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.platform.extensions.notificationManager

abstract class ActivityDetailsObservationRequester internal constructor() {
    @get:ChecksSdkIntAtLeast(Build.VERSION_CODES.TIRAMISU)
    protected val isInAndroidTiramisuOrHigher
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    protected val ActivityDetailsActivity.canAndShouldAskForPermission
        get() = isInAndroidTiramisuOrHigher &&
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
    protected val ActivityDetailsActivity.areNotificationsEnabled
        get() = notificationManager.areNotificationsEnabled()

    protected abstract val next: ActivityDetailsObservationRequester?

    internal abstract fun request(
        activity: ActivityDetailsActivity,
        isObserving: Boolean,
        onAcceptance: () -> Unit
    )
}