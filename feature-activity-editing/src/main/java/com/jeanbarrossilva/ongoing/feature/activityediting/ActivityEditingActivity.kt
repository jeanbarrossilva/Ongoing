package com.jeanbarrossilva.ongoing.feature.activityediting

import android.content.Context
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.argumentOf
import com.jeanbarrossilva.ongoing.platform.extensions.startActivity
import org.koin.android.ext.android.inject

class ActivityEditingActivity internal constructor(): ComposableActivity() {
    private val sessionManager by inject<SessionManager>()
    private val activityRegistry by inject<ActivityRegistry>()
    private val mode by argumentOf<ActivityEditingMode>(MODE_KEY)
    private val viewModel by viewModels<ActivityEditingViewModel> {
        ActivityEditingViewModel.createFactory(sessionManager, activityRegistry, mode)
    }

    @Composable
    override fun Content() {
        ActivityEditing(viewModel, onDone = onBackPressedDispatcher::onBackPressed)
    }

    companion object {
        private const val MODE_KEY = "mode"

        fun start(context: Context, mode: ActivityEditingMode) {
            context.startActivity<ActivityEditingActivity>(MODE_KEY to mode)
        }
    }
}