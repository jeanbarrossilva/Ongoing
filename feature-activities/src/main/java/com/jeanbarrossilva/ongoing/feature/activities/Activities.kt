package com.jeanbarrossilva.ongoing.feature.activities

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.ActivityCard
import com.jeanbarrossilva.ongoing.feature.activities.context.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activities.viewmodel.ActivitiesViewModel
import com.jeanbarrossilva.ongoing.platform.designsystem.component.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun Activities(viewModel: ActivitiesViewModel, modifier: Modifier = Modifier) {
    val activities by viewModel.activities.collectAsState(initial = emptyList())
    Activities(activities, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Activities(activities: List<ContextualActivity>, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.feature_activities)) }) }
    ) { padding ->
        Background(
            modifier
                .padding(Size.Spacing.ExtraLarge)
                .padding(padding)
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(Size.Spacing.Small)) {
                items(activities) {
                    ActivityCard(it, onClick = { }, Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivitiesPreview() {
    OngoingTheme {
        Activities(ContextualActivity.samples)
    }
}