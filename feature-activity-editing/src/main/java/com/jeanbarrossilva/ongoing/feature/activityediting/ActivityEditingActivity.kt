package com.jeanbarrossilva.ongoing.feature.activityediting

import android.content.Context
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.afollestad.materialdialogs.MaterialDialog
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showConfirmationDialogOnBackPressedWithChanges()
    }

    @Composable
    override fun Content() {
        ActivityEditing(
            viewModel,
            onNavigationRequest = onBackPressedDispatcher::onBackPressed,
            onDone = ::finish
        )
    }

    private fun showConfirmationDialogOnBackPressedWithChanges() {
        viewModel.onPropsChange {
            onBackPressedDispatcher.addCallback(this, enabled = mode.hasChanges(it)) {
                showConfirmationDialog()
            }
        }
    }

    private fun showConfirmationDialog() {
        MaterialDialog(this).show {
            title(R.string.feature_activity_editing_confirmation_dialog_title)
            message(R.string.feature_activity_editing_confirmation_dialog_message)
            positiveButton { finish() }
            negativeButton { dismiss() }
        }
    }

    companion object {
        internal const val MODE_KEY = "mode"

        fun start(context: Context, activity: ContextualActivity?) {
            val mode = activity.toActivityEditingMode()
            start(context, mode)
        }

        private fun start(context: Context, mode: ActivityEditingMode) {
            context.startActivity<ActivityEditingActivity>(MODE_KEY to mode)
        }
    }
}