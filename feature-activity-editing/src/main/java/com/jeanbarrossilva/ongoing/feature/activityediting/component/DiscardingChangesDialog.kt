package com.jeanbarrossilva.ongoing.feature.activityediting.component

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.jeanbarrossilva.ongoing.feature.activityediting.R

@Suppress("FunctionName")
internal fun DiscardingChangesDialog(context: Context, onNavigationRequest: () -> Unit) {
    MaterialDialog(context).show {
        title(R.string.feature_activity_editing_confirmation_dialog_title)
        message(R.string.feature_activity_editing_confirmation_dialog_message)
        positiveButton { onNavigationRequest() }
        negativeButton { dismiss() }
    }
}