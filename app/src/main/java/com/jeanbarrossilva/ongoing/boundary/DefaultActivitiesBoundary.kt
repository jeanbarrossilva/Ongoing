package com.jeanbarrossilva.ongoing.boundary

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity

internal class DefaultActivitiesBoundary: ActivitiesBoundary {
    override fun navigateToActivityDetails(context: Context, activity: ContextualActivity) {
        ActivityDetailsActivity.start(context, activity)
    }
}