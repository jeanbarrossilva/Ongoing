package com.jeanbarrossilva.ongoing.feature.activitydetails.component

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.ActivityStatusHistory.TAG
import com.jeanbarrossilva.ongoing.feature.activitydetails.domain.ContextActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.createSample
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.toJetLimeItem
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.BackgroundContentSizing
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.ifLoaded
import com.pushpal.jetlime.data.JetLimeItemsModel
import com.pushpal.jetlime.data.config.JetLimeViewConfig
import com.pushpal.jetlime.ui.JetLimeView

object ActivityStatusHistory {
    const val TAG = "activity_status_history"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun ActivityStatusHistory(
    activity: Loadable<ContextActivity>,
    modifier: Modifier = Modifier
) {
    val items = activity.ifLoaded(ContextActivity::statuses).orEmpty().map {
        it.toJetLimeItem()
    }

    JetLimeView(
        modifier.testTag(TAG),
        jetLimeItemsModel = JetLimeItemsModel(items),
        jetLimeViewConfig = JetLimeViewConfig(
            backgroundColor = MaterialTheme.colorScheme.background,
            lineColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivityStatusHistoryPreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            ActivityStatusHistory(
                Loadable.Loaded(ContextActivity.createSample(LocalContext.current))
            )
        }
    }
}