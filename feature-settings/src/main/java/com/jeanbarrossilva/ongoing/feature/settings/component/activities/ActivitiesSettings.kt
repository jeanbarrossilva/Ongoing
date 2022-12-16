package com.jeanbarrossilva.ongoing.feature.settings.component.activities

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alorma.compose.settings.ui.SettingsGroup
import com.jeanbarrossilva.ongoing.feature.settings.R
import com.jeanbarrossilva.ongoing.feature.settings.component.activities.clear.ActivitiesClearSetting
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.BackgroundContentSizing
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun ActivitiesSettings(
    hasActivities: Boolean,
    onClearRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingsGroup(
        modifier,
        title = {
            Text(
                stringResource(R.string.feature_settings_activities),
                style = MaterialTheme.typography.titleSmall
            )
        }
    ) {
        ActivitiesClearSetting(hasActivities, onClick = onClearRequest)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivitiesSettingsPreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            ActivitiesSettings(hasActivities = true, onClearRequest = { })
        }
    }
}