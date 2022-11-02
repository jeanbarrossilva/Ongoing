package com.jeanbarrossilva.ongoing.feature.activities

import android.content.Context
import androidx.navigation.NavController

interface ActivitiesBoundary {
    fun navigateToActivityDetails(navController: NavController, activityId: String)

    fun navigateToActivityEditing(context: Context)
}