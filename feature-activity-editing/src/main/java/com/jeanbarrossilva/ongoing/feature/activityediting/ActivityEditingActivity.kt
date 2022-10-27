package com.jeanbarrossilva.ongoing.feature.activityediting

import android.content.Context
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.feature.activityediting.extensions.toActivityEditingMode
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.argumentOf
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.startActivity
import org.koin.android.ext.android.inject

class ActivityEditingActivity internal constructor(): ComposableActivity() {
    private val activityRegistry by inject<ActivityRegistry>()
    private val mode by argumentOf<ActivityEditingMode>(MODE_KEY)
    private val viewModel by viewModels<ActivityEditingViewModel> {
        ActivityEditingViewModel.createFactory(activityRegistry, mode)
    }

    @Composable
    override fun Content() {
        val goBack = onBackPressedDispatcher::onBackPressed
        ActivityEditing(viewModel, onNavigationRequest = goBack, onDone = goBack)
    }

    companion object {
        private const val MODE_KEY = "mode"

        fun start(context: Context, activity: ContextualActivity?) {
            val mode = activity.toActivityEditingMode()
            start(context, mode)
        }

        private fun start(context: Context, mode: ActivityEditingMode) {
            context.startActivity<ActivityEditingActivity>(MODE_KEY to mode)
        }
    }
}