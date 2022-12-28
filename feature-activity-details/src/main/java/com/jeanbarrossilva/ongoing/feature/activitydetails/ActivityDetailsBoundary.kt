package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context

interface ActivityDetailsBoundary {
    fun navigateToActivityEditing(context: Context, activityId: String)

    companion object {
        internal val empty = object: ActivityDetailsBoundary {
            override fun navigateToActivityEditing(
                context: Context,
                activityId: String
            ) {
            }
        }
    }
}