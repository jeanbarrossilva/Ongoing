package com.jeanbarrossilva.ongoing.feature.activities

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activities.viewmodel.ActivitiesViewModel
import com.jeanbarrossilva.ongoing.feature.activities.viewmodel.ActivitiesViewModelFactory
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import org.koin.android.ext.android.inject

internal class ActivitiesActivity: ComposableActivity() {
    private val userRepository by inject<UserRepository>()
    private val activityRegistry by inject<ActivityRegistry>()
    private val boundary by inject<ActivitiesBoundary>()
    private val viewModel by viewModels<ActivitiesViewModel> {
        ActivitiesViewModelFactory(userRepository, activityRegistry)
    }

    @Composable
    override fun Content() {
        Activities(viewModel, boundary)
    }
}