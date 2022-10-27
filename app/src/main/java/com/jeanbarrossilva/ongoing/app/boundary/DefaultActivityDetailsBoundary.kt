package com.jeanbarrossilva.ongoing.app.boundary

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingActivity

internal class DefaultActivityDetailsBoundary: ActivityDetailsBoundary {
    override fun navigateToActivityEditing(context: Context, activity: ContextualActivity?) {
        ActivityEditingActivity.start(context, activity)
    }
}