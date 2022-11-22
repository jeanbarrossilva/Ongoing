package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.argumentOf
import com.jeanbarrossilva.ongoing.platform.extensions.Intent
import org.koin.android.ext.android.inject

class ActivityDetailsActivity: ComposableActivity() {
    private val session by inject<Session>()
    private val userRepository by inject<UserRepository>()
    private val activityRegistry by inject<ActivityRegistry>()
    private val observation by inject<Observation>()
    private val boundary by inject<ActivityDetailsBoundary>()
    private val activityId by argumentOf<String>(ACTIVITY_ID_KEY)
    private val viewModel by viewModels<ActivityDetailsViewModel> {
        ActivityDetailsViewModel.createFactory(
            session,
            userRepository,
            activityRegistry,
            observation,
            activityId
        )
    }

    private val navigator
        get() = ActivityDetailsBridge.getNavigator()

    override fun onDestroy() {
        super.onDestroy()
        ActivityDetailsBridge.clear()
    }

    @Composable
    override fun Content() {
        ActivityDetails(navigator, boundary, this, viewModel)
    }

    companion object {
        private const val ACTIVITY_ID_KEY = "activity_id"

        internal fun getIntent(context: Context, activityId: String): Intent {
            return Intent<ActivityDetailsActivity>(context, ACTIVITY_ID_KEY to activityId)
        }

        internal fun start(context: Context, activityId: String) {
            val intent = getIntent(context, activityId)
            context.startActivity(intent)
        }
    }
}