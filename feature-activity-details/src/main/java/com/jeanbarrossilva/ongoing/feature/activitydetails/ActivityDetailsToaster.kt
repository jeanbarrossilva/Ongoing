package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import android.widget.Toast

internal object ActivityDetailsToaster {
    fun onObservationToggle(context: Context, isObserving: Boolean) {
        val messageId = if (isObserving) {
            R.string.feature_activity_details_notify_enabled
        } else {
            R.string.feature_activity_details_notify_disabled
        }
        Toast.makeText(context, messageId, Toast.LENGTH_LONG).show()
    }
}