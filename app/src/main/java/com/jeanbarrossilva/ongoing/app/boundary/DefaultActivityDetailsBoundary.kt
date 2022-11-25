package com.jeanbarrossilva.ongoing.app.boundary

import android.content.Context
import com.jeanbarrossilva.ongoing.app.extensions.toActivityEditingMode
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingActivity

internal class DefaultActivityDetailsBoundary: ActivityDetailsBoundary {
    override fun navigateToActivityEditing(
        context: Context,
        contextualActivity: ContextualActivity?
    ) {
        val mode = contextualActivity.toActivityEditingMode()
        ActivityEditingActivity.start(context, mode)
    }
}