package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions._getParcelable
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.startActivity

class ActivityDetailsActivity internal constructor(): ComposableActivity() {
    private val contextualActivity
        get() = intent?.extras?._getParcelable<ContextualActivity>(ACTIVITY_KEY)

    @Composable
    override fun Content() {
        contextualActivity?.let {
            ActivityDetails(it, onNavigationRequest = onBackPressedDispatcher::onBackPressed)
        }
    }

    companion object {
        private const val ACTIVITY_KEY = "activity"

        fun start(context: Context, activity: ContextualActivity) {
            context.startActivity<ActivityDetailsActivity>(ACTIVITY_KEY to activity)
        }
    }
}