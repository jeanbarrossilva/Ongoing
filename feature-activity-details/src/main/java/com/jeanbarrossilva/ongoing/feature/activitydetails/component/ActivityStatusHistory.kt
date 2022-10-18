package com.jeanbarrossilva.ongoing.feature.activitydetails.component

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.toJetLimeItem
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.BackgroundContentSizing
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.pushpal.jetlime.data.JetLimeItemsModel
import com.pushpal.jetlime.data.config.JetLimeViewConfig
import com.pushpal.jetlime.ui.JetLimeView

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun ActivityStatusHistory(activity: ContextualActivity, modifier: Modifier = Modifier) {
    val item = activity.currentStatus.toJetLimeItem()
    val items = listOf(item)

    JetLimeView(
        modifier,
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
            ActivityStatusHistory(ContextualActivity.sample)
        }
    }
}