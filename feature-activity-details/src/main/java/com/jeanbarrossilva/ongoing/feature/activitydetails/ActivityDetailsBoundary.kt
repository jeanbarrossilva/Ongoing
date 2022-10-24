package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity

interface ActivityDetailsBoundary {
    fun navigateToActivityEditing(context: Context, activity: ContextualActivity?)
}