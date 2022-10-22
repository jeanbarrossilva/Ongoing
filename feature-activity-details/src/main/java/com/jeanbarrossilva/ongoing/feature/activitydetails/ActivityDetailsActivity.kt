package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.argumentOf
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.startActivity
import org.koin.android.ext.android.inject

class ActivityDetailsActivity internal constructor(): ComposableActivity() {
    private val boundary by inject<ActivityDetailsBoundary>()
    private val activity by argumentOf<ContextualActivity>(ACTIVITY_KEY)

    @Composable
    override fun Content() {
        ActivityDetails(
            boundary,
            activity,
            onNavigationRequest = onBackPressedDispatcher::onBackPressed
        )
    }

    companion object {
        private const val ACTIVITY_KEY = "activity"

        fun start(context: Context, activity: ContextualActivity) {
            context.startActivity<ActivityDetailsActivity>(ACTIVITY_KEY to activity)
        }
    }
}