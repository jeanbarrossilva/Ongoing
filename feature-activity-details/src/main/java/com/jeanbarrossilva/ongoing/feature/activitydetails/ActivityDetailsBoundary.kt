package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import kotlinx.coroutines.CoroutineScope

interface ActivityDetailsBoundary {
    fun navigateToActivityEditing(
        coroutineScope: CoroutineScope,
        context: Context,
        activityId: String
    )

    companion object {
        internal val empty = object: ActivityDetailsBoundary {
            override fun navigateToActivityEditing(
                coroutineScope: CoroutineScope,
                context: Context,
                activityId: String
            ) {
            }
        }
    }
}