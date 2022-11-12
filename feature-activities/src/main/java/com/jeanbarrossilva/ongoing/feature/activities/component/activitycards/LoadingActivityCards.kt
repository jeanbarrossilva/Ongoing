package com.jeanbarrossilva.ongoing.feature.activities.component.activitycards

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.platform.designsystem.component.LoadingIndicator
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun LoadingActivityCards(modifier: Modifier = Modifier) {
    Background(modifier) {
        LoadingIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun LoadingActivityCardsPreview() {
    OngoingTheme {
        LoadingActivityCards()
    }
}