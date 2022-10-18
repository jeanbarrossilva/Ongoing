package com.jeanbarrossilva.ongoing.feature.activitydetails.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.component.activityicon.ActivityIcon
import com.jeanbarrossilva.ongoing.context.registry.component.activityicon.ActivityIconSize
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.BackgroundContentSizing
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun ActivityHeadline(activity: ContextualActivity, modifier: Modifier = Modifier) {
    Column(
        modifier,
        Arrangement.spacedBy(Size.Spacing.xxxl),
        Alignment.CenterHorizontally
    ) {
        ActivityIcon(activity, ActivityIconSize.LARGE)

        Text(
            activity.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivityHeadlinePreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            ActivityHeadline(ContextualActivity.sample)
        }
    }
}