package com.jeanbarrossilva.ongoing.feature.activities.component.activitycard

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ActivityCard(
    activity: Loadable<ContextualActivity>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var prominence by remember { mutableStateOf(ActivityCardProminence.STILL) }
    var isPressed by remember { mutableStateOf(false) }
    val spacing = Size.Spacing.xxxl

    Card(
        onClick,
        modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    when (currentEvent.type) {
                        PointerEventType.Enter -> prominence = ActivityCardProminence.HOVERED
                        PointerEventType.Press -> isPressed = true
                        PointerEventType.Release -> isPressed = false
                        PointerEventType.Exit -> prominence = ActivityCardProminence.STILL
                    }
                }
            }
            .testTag(activity.ifLoaded(::getTag) ?: TAG),
        colors = cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        border = prominence.getBorderStroke(isPressed)
    ) {
        Row(Modifier.padding(spacing), Arrangement.spacedBy(spacing), Alignment.CenterVertically) {
            ActivityIcon(activity, ActivityIconSize.SMALL)
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
            onClick = { },
            Modifier.fillMaxWidth()
        )
    }
}