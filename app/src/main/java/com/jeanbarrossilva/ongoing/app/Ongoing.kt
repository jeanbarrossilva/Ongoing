package com.jeanbarrossilva.ongoing.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jeanbarrossilva.ongoing.app.extensions.activities
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activities.Activities
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary

@Composable
internal fun Ongoing(
    userRepository: UserRepository,
    activityRegistry: ActivityRegistry,
    activitiesBoundary: ActivitiesBoundary,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Activities.ROUTE, modifier) {
        activities(userRepository, activityRegistry, activitiesBoundary)
    }
}