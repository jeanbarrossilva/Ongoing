package com.jeanbarrossilva.ongoing.feature.activitydetails.bridge

import android.content.Context
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity

sealed class ActivityDetailsBridgeCrossing {
    data class Start(internal val activityId: String): ActivityDetailsBridgeCrossing() {
        override fun onCross(context: Context) {
            ActivityDetailsActivity.start(context, activityId)
        }
    }

    object RetentionOnly: ActivityDetailsBridgeCrossing() {
        override fun onCross(context: Context) {
        }
    }

    internal abstract fun onCross(context: Context)
}