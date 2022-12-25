package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.feature.activitydetails.infra.ActivityDetailsGateway
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.argumentOf
import com.jeanbarrossilva.ongoing.platform.extensions.Intent
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ActivityDetailsActivity internal constructor(): ComposableActivity() {
    private val boundary by inject<ActivityDetailsBoundary>()
    private val activityId by argumentOf<String>(ACTIVITY_ID_KEY)
    private val gateway by inject<ActivityDetailsGateway> {
        parametersOf(activityId, this, ::onObservationToggle)
    }
    private val viewModel by viewModels<ActivityDetailsViewModel> {
        ActivityDetailsViewModel.createFactory(gateway)
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
            viewModel,
            ::onObservationToggle,
            onNavigationRequest = onBackPressedDispatcher::onBackPressed
        )
    }

    private fun onObservationToggle(isObserving: Boolean) {
        ActivityDetailsToaster.onObservationToggle(this, isObserving)
    }

    companion object {
        internal const val ACTIVITY_ID_KEY = "activity_id"

        fun getIntent(context: Context?, activityId: String): Intent {
            return Intent<ActivityDetailsActivity>(context, ACTIVITY_ID_KEY to activityId)
        }

        fun start(context: Context, activityId: String) {
            val intent = getIntent(context, activityId)
            context.startActivity(intent)
        }
    }
}