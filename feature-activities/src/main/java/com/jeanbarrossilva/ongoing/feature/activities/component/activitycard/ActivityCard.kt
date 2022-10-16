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
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.component.ActivityHeadline
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.component.ActivityIcon
import com.jeanbarrossilva.ongoing.feature.activities.context.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ActivityCard(
    activity: ContextualActivity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var prominence by remember { mutableStateOf(ActivityCardProminence.STILL) }
    var isPressed by remember { mutableStateOf(false) }
    val spacing = Size.Spacing.ExtraLarge

    Card(
        onClick,
        modifier.pointerInput(Unit) {
            awaitPointerEventScope {
                when (currentEvent.type) {
                    PointerEventType.Enter -> prominence = ActivityCardProminence.HOVERED
                    PointerEventType.Press -> isPressed = true
                    PointerEventType.Release -> isPressed = false
                    PointerEventType.Exit -> prominence = ActivityCardProminence.STILL
                }
            }
        },
        colors = cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        border = prominence.getBorderStroke(isPressed)
    ) {
        Row(Modifier.padding(spacing), Arrangement.spacedBy(spacing), Alignment.CenterVertically) {
            ActivityIcon(activity)
            ActivityHeadline(activity)
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivityCardPreview() {
    OngoingTheme {
        ActivityCard(ContextualActivity.sample, onClick = { }, Modifier.fillMaxWidth())
    }
}