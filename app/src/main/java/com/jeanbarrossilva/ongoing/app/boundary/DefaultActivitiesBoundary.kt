package com.jeanbarrossilva.ongoing.app.boundary

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity

internal class DefaultActivitiesBoundary: ActivitiesBoundary {
    override fun navigateToActivityDetails(context: Context, activityId: String) {
        ActivityDetailsActivity.start(context, activityId)
    }
}