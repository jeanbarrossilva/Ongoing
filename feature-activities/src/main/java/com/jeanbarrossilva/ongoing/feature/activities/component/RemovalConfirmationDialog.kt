package com.jeanbarrossilva.ongoing.feature.activities.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesSelection
import com.jeanbarrossilva.ongoing.feature.activities.R

internal const val REMOVAL_CONFIRMATION_DIALOG_TAG = "removal_confirmation_dialog"
internal const val REMOVAL_CONFIRMATION_DIALOG_CONFIRMATION_BUTTON_TAG =
    "removal_confirmation_dialog_confirmation_button"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun RemovalConfirmationDialog(
    selection: ActivitiesSelection.On,
    onDismissalRequest: () -> Unit,
    onConfirmationRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissalRequest,
        confirmButton = {
            TextButton(
                onClick = onConfirmationRequest,
                Modifier.testTag(REMOVAL_CONFIRMATION_DIALOG_CONFIRMATION_BUTTON_TAG)
            ) {
                Text(stringResource(android.R.string.ok))
            }
        },
        modifier.testTag(REMOVAL_CONFIRMATION_DIALOG_TAG),
        dismissButton = {
            TextButton(onClick = onDismissalRequest) {
                Text(stringResource(android.R.string.cancel))
            }
        },
        text = {
            Text(
                pluralStringResource(
                    R.plurals.feature_activities_removal_confirmation,
                    selection.selected.size
                )
            )
        }
    )
}