package com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus
import com.jeanbarrossilva.ongoing.context.registry.extensions.title
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.ContentAlpha
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun StatusIndicator(status: ContextualStatus?, modifier: Modifier = Modifier) {
    val containerColor = when (status) {
        ContextualStatus.TO_DO, ContextualStatus.ONGOING, null ->
            MaterialTheme.colorScheme.secondaryContainer
        ContextualStatus.DONE -> MaterialTheme.colorScheme.primary
    }
    val alpha = when (status) {
        ContextualStatus.TO_DO, ContextualStatus.ONGOING, null -> ContentAlpha.MEDIUM
        ContextualStatus.DONE -> ContentAlpha.OPAQUE
    }

    Card(
        modifier.height(TertiaryInfo.Height),
        MaterialTheme.shapes.small,
        cardColors(containerColor)
    ) {
        Box(
            Modifier.padding(
                horizontal = Size.Spacing.xs + Size.Spacing.xxs,
                vertical = Size.Spacing.xs
            )
        ) {
            Text(
                status?.title?.uppercase().orEmpty(),
                Modifier.alpha(alpha),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ToDoStatusIndicatorPreview() {
    OngoingTheme {
        StatusIndicator(ContextualStatus.TO_DO)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun OngoingStatusIndicatorPreview() {
    OngoingTheme {
        StatusIndicator(ContextualStatus.ONGOING)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DoneStatusIndicatorPreview() {
    OngoingTheme {
        StatusIndicator(ContextualStatus.DONE)
    }
}