package com.jeanbarrossilva.ongoing.feature.activitydetails.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.component.activityicon.ActivityIcon
import com.jeanbarrossilva.ongoing.context.registry.component.activityicon.ActivityIconSize
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.activityheadline.ActivityHeadlineName
import com.jeanbarrossilva.ongoing.feature.activitydetails.domain.ContextActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.contextualIcon
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.createSample
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.BackgroundContentSizing
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.map

@Composable
internal fun ActivityHeadline(
    activity: Loadable<ContextActivity>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier,
        Arrangement.spacedBy(Size.Spacing.xxxl),
        Alignment.CenterHorizontally
    ) {
        ActivityIcon(
            activity.map(ContextActivity::contextualIcon),
            ActivityIconSize.LARGE,
            isSelected = false
        )

        ActivityHeadlineName(activity)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivityHeadlinePreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            ActivityHeadline(Loadable.Loaded(ContextActivity.createSample(LocalContext.current)))
        }
    }
}