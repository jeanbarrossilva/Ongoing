package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.argumentOf
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.startActivity
import org.koin.android.ext.android.inject

class ActivityDetailsActivity internal constructor(): ComposableActivity() {
    private val userRepository by inject<UserRepository>()
    private val activityRegistry by inject<ActivityRegistry>()
    private val boundary by inject<ActivityDetailsBoundary>()
    private val activityId by argumentOf<String>(ACTIVITY_ID_KEY)
    private val viewModel by viewModels<ActivityDetailsViewModel> {
        ActivityDetailsViewModel.createFactory(userRepository, activityRegistry)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setActivityId(activityId)
    }

    @Composable
    override fun Content() {
        ActivityDetails(
            boundary,
            viewModel,
            onNavigationRequest = onBackPressedDispatcher::onBackPressed
        )
    }

    companion object {
        private const val ACTIVITY_ID_KEY = "activity"

        fun start(context: Context, activityId: String) {
            context.startActivity<ActivityDetailsActivity>(ACTIVITY_ID_KEY to activityId)
        }
    }
}