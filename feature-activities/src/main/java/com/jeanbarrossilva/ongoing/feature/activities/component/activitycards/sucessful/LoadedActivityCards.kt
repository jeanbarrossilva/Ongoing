package com.jeanbarrossilva.ongoing.feature.activities.component.activitycards.sucessful

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun LoadedActivityCards(
    activities: List<ContextualActivity>,
    contentPadding: PaddingValues,
    onActivityDetailsRequest: (activity: ContextualActivity) -> Unit,
    modifier: Modifier = Modifier
) {
    activities.ifEmpty {
        LoadedEmptyActivityCards(modifier)
    }

    LoadedPopulatedActivityCards(
        activities,
        contentPadding,
        onActivityDetailsRequest,
        modifier
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadedActivityCardsPreview() {
    OngoingTheme {
        LoadedActivityCards(
            ContextualActivity.samples,
            contentPadding = PaddingValues(0.dp),
            onActivityDetailsRequest = { }
        )
    }
}