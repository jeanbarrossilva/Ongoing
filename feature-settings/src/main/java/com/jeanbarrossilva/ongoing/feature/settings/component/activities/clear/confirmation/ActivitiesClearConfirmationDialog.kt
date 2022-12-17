package com.jeanbarrossilva.ongoing.feature.settings.component.activities.clear.confirmation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.jeanbarrossilva.ongoing.feature.settings.R
import com.jeanbarrossilva.ongoing.feature.settings.component.activities.clear.confirmation.ActivitiesClearConfirmationDialog.TAG

object ActivitiesClearConfirmationDialog {
    const val TAG = "activities_clear_confirmation_dialog"
}

@Composable
internal fun ActivitiesClearConfirmationDialog(
    onConfirmationRequest: () -> Unit,
    onDismissalRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = { ActivitiesClearConfirmationButton(onClick = onConfirmationRequest) },
        modifier.testTag(TAG),
        dismissButton = {
            TextButton(onClick = onDismissalRequest) {
                Text(stringResource(android.R.string.cancel))
            }
        },
        title = {
            Text(stringResource(R.string.feature_settings_activities_clear_confirmation_title))
        },
        text = {
            Text(
                stringResource(R.string.feature_settings_activities_clear_confirmation_description)
            )
        }
    )
}