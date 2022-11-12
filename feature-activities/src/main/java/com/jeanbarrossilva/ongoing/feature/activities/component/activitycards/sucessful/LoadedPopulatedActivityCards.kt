package com.jeanbarrossilva.ongoing.feature.activities.component.activitycards.sucessful

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.ActivityCard
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable

@Composable
internal fun LoadedPopulatedActivityCards(
    activities: List<ContextualActivity>,
    onActivityDetailsRequest: (activity: ContextualActivity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(Size.Spacing.s)) {
        items(activities) {
            ActivityCard(
                Loadable.Loaded(it),
                onClick = { onActivityDetailsRequest(it) },
                Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadedPopulatedActivityCardsPreview() {
    OngoingTheme {
        LoadedPopulatedActivityCards(ContextualActivity.samples, onActivityDetailsRequest = { })
    }
}