package com.jeanbarrossilva.ongoing.feature.settings.component.activities.clear

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.jeanbarrossilva.ongoing.feature.settings.R
import com.jeanbarrossilva.ongoing.feature.settings.component.activities.clear.ActivitiesClearSetting.TAG
import com.jeanbarrossilva.ongoing.feature.settings.component.activities.clear.confirmation.ActivitiesClearConfirmationDialog
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.BackgroundContentSizing
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.ContentAlpha
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

object ActivitiesClearSetting {
    const val TAG = "activities_clear_setting"
}

@Composable
internal fun ActivitiesClearSetting(
    hasActivities: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val title = stringResource(R.string.feature_settings_activities_clear)
    val contentColor = MaterialTheme.colorScheme.error
    var isConfirmationDialogVisible by remember { mutableStateOf(false) }

    if (isConfirmationDialogVisible) {
        ActivitiesClearConfirmationDialog(
            onConfirmationRequest = {
                onClick()
                isConfirmationDialogVisible = false
            },
            onDismissalRequest = { isConfirmationDialogVisible = false }
        )
    }

    if (hasActivities) {
        SettingsMenuLink(
            modifier.testTag(TAG),
            icon = { Icon(Icons.Rounded.Delete, contentDescription = title, tint = contentColor) },
            title = { Text(title, color = contentColor) },
            subtitle = {
                Text(
                    stringResource(R.string.feature_settings_activities_clear_description),
                    Modifier.alpha(ContentAlpha.MEDIUM),
                    contentColor
                )
            },
            onClick = { isConfirmationDialogVisible = true }
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivitiesClearSettingPreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            ActivitiesClearSetting(hasActivities = true, onClick = { })
        }
    }
}