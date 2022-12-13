package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.feature.activitydetails.bridge.ActivityDetailsBridge
import com.jeanbarrossilva.ongoing.feature.activitydetails.observation.ActivityDetailsObservationRequesterFactory
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.argumentOf
import com.jeanbarrossilva.ongoing.platform.extensions.Intent

class ActivityDetailsActivity internal constructor(): ComposableActivity() {
    private val activityId by argumentOf<String>(ACTIVITY_ID_KEY)
    private val viewModel by viewModels<ActivityDetailsViewModel> {
        ActivityDetailsViewModel.createFactory(
            ActivityDetailsBridge.getSession(),
            ActivityDetailsBridge.getActivityRegistry(),
            ActivityDetailsBridge.getObservation(),
            ActivityDetailsBridge.getFetcher(),
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

    override fun onDestroy() {
        super.onDestroy()
        ActivityDetailsBridge.clear()
    }

    @Composable
    override fun Content() {
        ActivityDetails(
            ActivityDetailsBridge.getBoundary(),
            this,
            viewModel,
            ActivityDetailsObservationRequesterFactory.create(),
            onNavigationRequest = onBackPressedDispatcher::onBackPressed
        )
    }

    companion object {
        private const val ACTIVITY_ID_KEY = "activity_id"

        fun getIntent(context: Context?, activityId: String): Intent {
            return Intent<ActivityDetailsActivity>(context, ACTIVITY_ID_KEY to activityId)
        }

        internal fun start(context: Context, activityId: String) {
            val intent = getIntent(context, activityId)
            context.startActivity(intent)
        }
    }
}