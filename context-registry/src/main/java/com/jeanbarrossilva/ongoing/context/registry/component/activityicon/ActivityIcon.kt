package com.jeanbarrossilva.ongoing.context.registry.component.activityicon

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.registry.R

@Composable
fun ActivityIcon(
    activity: ContextualActivity,
    size: ActivityIconSize,
    modifier: Modifier = Modifier
) {
    Icon(
        activity.icon.vector,
        contentDescription =
            stringResource(R.string.platform_registry_activity_card_icon_content_description),
        modifier
            .clip(size.shape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(size.padding)
            .requiredSize(size.adaptedValue),
        MaterialTheme.colorScheme.primary
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SmallActivityIconPreview() {
    OngoingTheme {
        ActivityIcon(ContextualActivity.sample, ActivityIconSize.SMALL)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LargeActivityIconPreview() {
    OngoingTheme {
        ActivityIcon(ContextualActivity.sample, ActivityIconSize.LARGE)
    }
}