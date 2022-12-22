package com.jeanbarrossilva.ongoing.feature.activities.component.activitycards

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesSelection
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycards.sucessful.LoadedActivityCards
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toSerializableList
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList

@Composable
internal fun ActivityCards(
    activities: Loadable<SerializableList<ContextualActivity>>,
    selection: ActivitiesSelection,
    onSelectionChange: (selection: ActivitiesSelection) -> Unit,
    contentPadding: PaddingValues,
    onActivityDetailsRequest: (activity: ContextualActivity) -> Unit,
    modifier: Modifier = Modifier
) {
    when (activities) {
        is Loadable.Loading -> LoadingActivityCards()
        is Loadable.Loaded -> LoadedActivityCards(
            activities.value,
            selection,
            onSelectionChange,
            contentPadding,
            onActivityDetailsRequest,
            modifier
        )
        is Loadable.Failed -> FailedActivityCards()
    }
}

@Composable
private fun ActivityCards(
    activities: Loadable<SerializableList<ContextualActivity>>,
    modifier: Modifier = Modifier
) {
    ActivityCards(
        activities,
        ActivitiesSelection.Off,
        onSelectionChange = { },
        PaddingValues(0.dp),
        onActivityDetailsRequest = { },
        modifier
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SuccessfulActivityCardsPreview() {
    OngoingTheme {
        ActivityCards(Loadable.Loaded(ContextualActivity.samples.toSerializableList()))
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadingActivityCardsPreview() {
    OngoingTheme {
        ActivityCards(Loadable.Loading())
    }
}