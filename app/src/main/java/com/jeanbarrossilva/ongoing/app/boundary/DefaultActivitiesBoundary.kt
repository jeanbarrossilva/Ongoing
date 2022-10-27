package com.jeanbarrossilva.ongoing.app.boundary

import android.content.Context
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingActivity

internal class DefaultActivitiesBoundary: ActivitiesBoundary {
    override fun navigateToActivityDetails(context: Context, activityId: String) {
        ActivityDetailsActivity.start(context, activityId)
    }

    override fun navigateToActivityEditing(context: Context) {
        ActivityEditingActivity.start(context, activity = null)
    }
}