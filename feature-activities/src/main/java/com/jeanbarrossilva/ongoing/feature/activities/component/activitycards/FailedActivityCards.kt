package com.jeanbarrossilva.ongoing.feature.activities.component.activitycards

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.activities.R
import com.jeanbarrossilva.ongoing.platform.designsystem.component.Message
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun FailedActivityCards(modifier: Modifier = Modifier) {
    Background(modifier.padding(Size.Spacing.s)) {
        Message(
            Icons.Rounded.Error,
            title = { Text(stringResource(R.string.feature_activities_list_failed_message_title)) },
            description = {
                Text(stringResource(R.string.feature_activities_list_failed_message_description))
            },
            Modifier.align(Alignment.Center)
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FailedActivityCardsPreview() {
    OngoingTheme {
        FailedActivityCards()
    }
}