package com.jeanbarrossilva.ongoing.feature.activitydetails.component.activityheadline

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.activityheadline.ActivityHeadlineName.TAG
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.BackgroundContentSizing
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.PlaceholderSize
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.placeholder
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.ifLoaded

object ActivityHeadlineName {
    const val TAG = "activity_headline_name"
}

@Composable
internal fun ActivityHeadlineName(
    activity: Loadable<ContextualActivity>,
    modifier: Modifier = Modifier
) {
    val textStyle = MaterialTheme.typography.titleLarge

    Text(
        activity.ifLoaded(ContextualActivity::name).orEmpty(),
        modifier
            .placeholder(
                PlaceholderSize.Text(width = 172.dp) { textStyle },
                isVisible = activity is Loadable.Loading
            )
            .testTag(TAG),
        textAlign = TextAlign.Center,
        style = textStyle
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadedActivityHeadlineNamePreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            ActivityHeadlineName(Loadable.Loaded(ContextualActivity.sample))
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadingActivityHeadlineNamePreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            ActivityHeadlineName(Loadable.Loading())
        }
    }
}