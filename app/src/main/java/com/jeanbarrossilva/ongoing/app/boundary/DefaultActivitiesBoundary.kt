package com.jeanbarrossilva.ongoing.app.boundary

import android.content.Context
import androidx.navigation.NavController
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetails
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingActivity

internal class DefaultActivitiesBoundary: ActivitiesBoundary {
    override fun navigateToActivityDetails(navController: NavController, activityId: String) {
        val route = ActivityDetails.route(activityId)
        navController.navigate(route)
    }

    override fun navigateToActivityEditing(context: Context) {
        ActivityEditingActivity.start(context, activity = null)
    }
}