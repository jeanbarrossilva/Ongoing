package com.jeanbarrossilva.ongoing.feature.activityediting

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.argumentOf
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.startActivity
import org.koin.android.ext.android.inject

class ActivityEditingActivity internal constructor(): ComposableActivity() {
    private val activityRegistry by inject<ActivityRegistry>()
    private val activity by argumentOf<ContextualActivity?>(ACTIVITY_KEY)
    private val viewModel by viewModels<ActivityEditingViewModel> {
        ActivityEditingViewModel.createFactory(activityRegistry)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.activity.value = activity
    }

    @Composable
    override fun Content() {
        val goBack = onBackPressedDispatcher::onBackPressed
        ActivityEditing(viewModel, onNavigationRequest = goBack, onDone = goBack)
    }

    companion object {
        private const val ACTIVITY_KEY = "activity"

        fun start(context: Context, activity: ContextualActivity?) {
            context.startActivity<ActivityEditingActivity>(ACTIVITY_KEY to activity)
        }
    }
}