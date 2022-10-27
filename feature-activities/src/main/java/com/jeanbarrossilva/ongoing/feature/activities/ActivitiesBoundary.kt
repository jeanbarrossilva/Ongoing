package com.jeanbarrossilva.ongoing.feature.activities

import android.content.Context

interface ActivitiesBoundary {
    fun navigateToActivityDetails(context: Context, activityId: String)

    fun navigateToActivityEditing(context: Context)
}