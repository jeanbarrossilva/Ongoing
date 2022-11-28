package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.feature.activitydetails.observation.ActivityDetailsObservationRequesterFactory
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.argumentOf
import com.jeanbarrossilva.ongoing.platform.extensions.Intent
import org.koin.android.ext.android.inject

class ActivityDetailsActivity internal constructor(): ComposableActivity() {
    private val session by inject<Session>()
    private val activityRegistry by inject<ActivityRegistry>()
    private val observation by inject<Observation>()
    private val fetcher by inject<ContextualActivitiesFetcher>()
    private val boundary by inject<ActivityDetailsBoundary>()
    private val activityId by argumentOf<String>(ACTIVITY_ID_KEY)
    private val viewModel by viewModels<ActivityDetailsViewModel> {
        ActivityDetailsViewModel.createFactory(
            session,
            activityRegistry,
            observation,
            fetcher,
            activityId
        )
    }

    internal val notificationsPermissionResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.setObserving(true)
            }
        }

    override fun onResume() {
        super.onResume()
        viewModel.fetch()
    }

    @Composable
    override fun Content() {
        ActivityDetails(
            boundary,
            this,
            viewModel,
            ActivityDetailsObservationRequesterFactory.create(),
            onNavigationRequest = onBackPressedDispatcher::onBackPressed
        )
    }

    companion object {
        private const val ACTIVITY_ID_KEY = "activity_id"

        fun getIntent(context: Context, activityId: String): Intent {
            return Intent<ActivityDetailsActivity>(context, ACTIVITY_ID_KEY to activityId)
        }

        fun start(context: Context, activityId: String) {
            val intent = getIntent(context, activityId)
            context.startActivity(intent)
        }
    }
}