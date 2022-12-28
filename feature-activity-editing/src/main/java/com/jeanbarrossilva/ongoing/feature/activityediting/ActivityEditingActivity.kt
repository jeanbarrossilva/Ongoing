package com.jeanbarrossilva.ongoing.feature.activityediting

import android.content.Context
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.feature.activityediting.infra.ActivityEditingGateway
import com.jeanbarrossilva.ongoing.feature.activityediting.infra.inmemory.EditingMode
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.argumentOf
import com.jeanbarrossilva.ongoing.platform.extensions.startActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ActivityEditingActivity internal constructor(): ComposableActivity() {
    private val activityRegistry by inject<ActivityRegistry>()
    private val activityId by argumentOf<String?>(ACTIVITY_ID_KEY)
    private val mode by lazy {
        activityId
            ?.let { EditingMode.Modification(activityRegistry, it) }
            ?: EditingMode.Addition(activityRegistry)
    }
    private val gateway by inject<ActivityEditingGateway> { parametersOf(mode) }
    private val viewModel by viewModels<ActivityEditingViewModel> {
        ActivityEditingViewModel.createFactory(gateway)
    }

    @Composable
    override fun Content() {
        ActivityEditing(viewModel, onDone = onBackPressedDispatcher::onBackPressed)
    }

    companion object {
        private const val ACTIVITY_ID_KEY = "activity_id"

        fun start(context: Context, activityId: String?) {
            context.startActivity<ActivityEditingActivity>(ACTIVITY_ID_KEY to activityId)
        }
    }
}