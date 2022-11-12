package com.jeanbarrossilva.ongoing.feature.activities.component.activitycards.sucessful

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun LoadedActivityCards(
    activities: List<ContextualActivity>,
    onActivityDetailsRequest: (activity: ContextualActivity) -> Unit,
    modifier: Modifier = Modifier
) {
    activities.ifEmpty { LoadedEmptyActivityCards(modifier) }
    LoadedPopulatedActivityCards(activities, onActivityDetailsRequest, modifier)
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadedActivityCardsPreview() {
    OngoingTheme {
        LoadedActivityCards(ContextualActivity.samples, onActivityDetailsRequest = { })
    }
}