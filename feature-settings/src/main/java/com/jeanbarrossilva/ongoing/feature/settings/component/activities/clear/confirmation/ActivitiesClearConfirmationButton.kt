package com.jeanbarrossilva.ongoing.feature.settings.component.activities.clear.confirmation

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.settings.component.activities.clear.confirmation.ActivitiesClearConfirmationButton.TAG
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.BackgroundContentSizing
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

object ActivitiesClearConfirmationButton {
    const val TAG = "activities_clear_confirmation_button"
}

@Composable
internal fun ActivitiesClearConfirmationButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(onClick, modifier.testTag(TAG)) {
        Text(stringResource(android.R.string.ok))
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivitiesClearConfirmationButtonPreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            ActivitiesClearConfirmationButton(onClick = { })
        }
    }
}