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
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.context.registry.component.activityicon.ActivityIcon
import com.jeanbarrossilva.ongoing.context.registry.component.activityicon.ActivityIconSize
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.BackgroundContentSizing
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.PlaceholderSize
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.placeholder
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable

@Composable
internal fun ActivityHeadline(
    activity: Loadable<ContextualActivity>,
    modifier: Modifier = Modifier
) {
    val nameTextStyle = MaterialTheme.typography.titleLarge

    Column(
        modifier,
        Arrangement.spacedBy(Size.Spacing.xxxl),
        Alignment.CenterHorizontally
    ) {
        ActivityIcon(activity, ActivityIconSize.LARGE)

        Text(
            activity.ifLoaded(ContextualActivity::name).orEmpty(),
            Modifier.placeholder(
                PlaceholderSize.Text(width = 172.dp) { nameTextStyle },
                isVisible = activity is Loadable.Loading
            ),
            textAlign = TextAlign.Center,
            style = nameTextStyle
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivityHeadlinePreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            ActivityHeadline(Loadable.Loaded(ContextualActivity.sample))
        }
    }
}