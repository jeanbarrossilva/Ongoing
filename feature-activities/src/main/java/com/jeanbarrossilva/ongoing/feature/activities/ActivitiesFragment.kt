package com.jeanbarrossilva.ongoing.feature.activities

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.activities.viewmodel.ActivitiesViewModel
import com.jeanbarrossilva.ongoing.feature.activities.viewmodel.ActivitiesViewModelFactory
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableFragment
import org.koin.android.ext.android.inject

internal class ActivitiesFragment: ComposableFragment() {
    private val userRepository by inject<UserRepository>()
    private val activityRegistry by inject<ActivityRegistry>()
    private val viewModel by viewModels<ActivitiesViewModel> {
        ActivitiesViewModelFactory(userRepository, activityRegistry)
    }

    @Composable
    override fun Content() {
        Activities(viewModel)
    }
}