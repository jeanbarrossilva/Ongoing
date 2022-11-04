package com.jeanbarrossilva.ongoing.feature.activityediting.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.jeanbarrossilva.ongoing.feature.activityediting.R
import com.jeanbarrossilva.ongoing.feature.activityediting.component.ConfirmationDialog.TAG

internal object ConfirmationDialog {
    const val TAG = "confirmation_dialog"
}

@Composable
internal fun ConfirmationDialog(
    onConfirmationRequest: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmationRequest) {
                Text(stringResource(android.R.string.ok))
            }
        },
        modifier.testTag(TAG),
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(android.R.string.cancel))
            }
        },
        title = {
            Text(stringResource(R.string.feature_activity_editing_confirmation_dialog_title))
        },
        text = { Text(stringResource(R.string.feature_activity_editing_confirmation_dialog_text)) }
    )
}