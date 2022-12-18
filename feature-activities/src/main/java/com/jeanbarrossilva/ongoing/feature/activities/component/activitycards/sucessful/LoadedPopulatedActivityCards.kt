package com.jeanbarrossilva.ongoing.feature.activities.component.activitycards.sucessful

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesSelection
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.ActivityCard
import com.jeanbarrossilva.ongoing.feature.activities.extensions.ifOff
import com.jeanbarrossilva.ongoing.feature.activities.extensions.ifOn
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable

@Composable
internal fun LoadedPopulatedActivityCards(
    activities: List<ContextualActivity>,
    contentPadding: PaddingValues,
    onActivityDetailsRequest: (activity: ContextualActivity) -> Unit,
    modifier: Modifier = Modifier
) {
    var selection by remember {
        mutableStateOf<ActivitiesSelection>(ActivitiesSelection.Off)
    }

    LazyColumn(
        modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(Size.Spacing.s)
    ) {
        items(activities) {
            ActivityCard(
                Loadable.Loaded(it),
                isSelected = selection.ifOn { isSelected(it) } ?: false,
                onClick = {
                    selection.ifOn { selection = toggle(it) } ?: onActivityDetailsRequest(it)
                },
                onLongClick = {
                    selection.ifOff {
                        selection = ActivitiesSelection.On(it)
                    }
                },
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
        LoadedPopulatedActivityCards(
            ContextualActivity.samples,
            contentPadding = PaddingValues(0.dp),
            onActivityDetailsRequest = { }
        )
    }
}