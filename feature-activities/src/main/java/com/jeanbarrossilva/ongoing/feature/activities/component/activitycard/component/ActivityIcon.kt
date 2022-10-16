package com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.activities.R
import com.jeanbarrossilva.ongoing.feature.activities.context.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun ActivityIcon(activity: ContextualActivity, modifier: Modifier = Modifier) {
    Icon(
        activity.icon.vector,
        contentDescription = stringResource(
            R.string.feature_activities_activity_card_icon_content_description
        ),
        modifier
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(Size.Spacing.Small),
        MaterialTheme.colorScheme.primary
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivityIconPreview() {
    OngoingTheme {
        ActivityIcon(ContextualActivity.sample)
    }
}