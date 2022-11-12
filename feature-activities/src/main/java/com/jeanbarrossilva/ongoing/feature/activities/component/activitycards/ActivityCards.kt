package com.jeanbarrossilva.ongoing.feature.activities.component.activitycards

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycards.sucessful.LoadedActivityCards
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toSerializableList
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList

@Composable
internal fun ActivityCards(
    activities: Loadable<SerializableList<ContextualActivity>>,
    onActivityDetailsRequest: (activity: ContextualActivity) -> Unit,
    modifier: Modifier = Modifier
) {
    when (activities) {
        is Loadable.Loaded ->
            LoadedActivityCards(activities.value, onActivityDetailsRequest, modifier)
        is Loadable.Loading -> LoadingActivityCards()
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SuccessfulActivityCardsPreview() {
    OngoingTheme {
        ActivityCards(
            Loadable.Loaded(ContextualActivity.samples.toSerializableList()),
            onActivityDetailsRequest = { }
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadingActivityCardsPreview() {
    OngoingTheme {
        ActivityCards(Loadable.Loading(), onActivityDetailsRequest = { })
    }
}