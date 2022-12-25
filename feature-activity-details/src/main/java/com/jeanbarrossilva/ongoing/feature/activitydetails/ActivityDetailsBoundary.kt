package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity

interface ActivityDetailsBoundary {
    fun navigateToActivityEditing(context: Context, contextualActivity: ContextualActivity?)

    companion object {
        internal val empty = object: ActivityDetailsBoundary {
            override fun navigateToActivityEditing(
                context: Context,
                contextualActivity: ContextualActivity?
            ) {
            }
        }
    }
}