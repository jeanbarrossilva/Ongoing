package com.jeanbarrossilva.ongoing.feature.activities.component.activitycard

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.context.registry.component.activityicon.ActivityIcon
import com.jeanbarrossilva.ongoing.context.registry.component.activityicon.ActivityIconSize
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.ActivityCard.TAG
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.ActivityCard.getTag
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.component.ActivityHeadline
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.ifLoaded

object ActivityCard {
    const val TAG = "activity_card"

    fun getTag(activity: ContextualActivity): String {
        return "${TAG}_${activity.id}"
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ActivityCard(
    activity: Loadable<ContextualActivity>,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = Size.Spacing.xxxl

    Card(
        modifier.testTag(activity.ifLoaded(::getTag) ?: TAG),
        colors = cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            Modifier
                .combinedClickable(onLongClick = onLongClick, onClick = onClick)
                .padding(spacing)
                .fillMaxWidth(),
            Arrangement.spacedBy(spacing),
            Alignment.CenterVertically
        ) {
            ActivityIcon(activity, ActivityIconSize.SMALL, isSelected)
            ActivityHeadline(activity)
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivityCardPreview() {
    OngoingTheme {
        ActivityCard(
            Loadable.Loaded(ContextualActivity.sample),
            isSelected = false,
            onClick = { },
            onLongClick = { }
        )
    }
}