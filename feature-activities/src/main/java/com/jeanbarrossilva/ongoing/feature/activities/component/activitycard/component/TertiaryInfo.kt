package com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.feature.activities.context.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

internal object TertiaryInfo {
    val Height = 24.dp
}

@Composable
internal fun TertiaryInfo(activity: ContextualActivity, modifier: Modifier = Modifier) {
    Row(modifier, Arrangement.spacedBy(Size.Spacing.Small), Alignment.CenterVertically) {
        Avatar(activity.owner)
        StatusIndicator(activity.currentStatus)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TertiaryInfoPreview() {
    OngoingTheme {
        TertiaryInfo(ContextualActivity.sample)
    }
}