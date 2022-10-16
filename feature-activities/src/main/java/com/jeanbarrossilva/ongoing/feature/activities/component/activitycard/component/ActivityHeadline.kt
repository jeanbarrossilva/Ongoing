package com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.activities.context.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun ActivityHeadline(activity: ContextualActivity, modifier: Modifier = Modifier) {
    Column(modifier, Arrangement.spacedBy(Size.Spacing.Small)) {
        Text(
            activity.name,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = MaterialTheme.typography.titleSmall
        )

        TertiaryInfo(activity)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivityHeadlinePreview() {
    OngoingTheme {
        ActivityHeadline(ContextualActivity.sample)
    }
}