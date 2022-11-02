package com.jeanbarrossilva.ongoing.app

import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import org.koin.android.ext.android.inject

internal class OngoingActivity: ComposableActivity() {
    private val userRepository by inject<UserRepository>()
    private val activityRegistry by inject<ActivityRegistry>()
    private val activitiesBoundary by inject<ActivitiesBoundary>()

    @Composable
    override fun Content() {
        Ongoing(userRepository, activityRegistry, activitiesBoundary)
    }
}