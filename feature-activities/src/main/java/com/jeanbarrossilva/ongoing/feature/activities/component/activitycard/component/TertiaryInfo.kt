package com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.component.TertiaryInfo.Height
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.context.registry.component.Avatar

internal object TertiaryInfo {
    val Height = 24.dp
}

@Composable
internal fun TertiaryInfo(activity: ContextualActivity, modifier: Modifier = Modifier) {
    Row(modifier, Arrangement.spacedBy(Size.Spacing.s), Alignment.CenterVertically) {
        Avatar(activity.owner, Modifier.size(Height))
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