package com.jeanbarrossilva.ongoing.feature.activityediting

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.component.ActivityCurrentStatusDropdownField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.ActivityNameTextField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarRelevance
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun ActivityEditing(
    viewModel: ActivityEditingViewModel,
    onNavigationRequest: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity by viewModel.activity.collectAsState()

    ActivityEditing(
        activity,
        onChange = { viewModel.activity.value = it },
        onNavigationRequest,
        onDone = {
            viewModel.save()
            onDone()
        },
        modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActivityEditing(
    activity: ContextualActivity?,
    onChange: (activity: ContextualActivity) -> Unit,
    onNavigationRequest: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = Size.Spacing.xxl

    Scaffold(
        modifier,
        topBar = {
            TopAppBar(TopAppBarRelevance.Subsequent(onNavigationRequest)) {
                Text(stringResource(R.string.feature_activity_editing))
            }
        },
        floatingActionButton = { FloatingActionButton(onClick = onDone) },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        Background(
            Modifier
                .padding(spacing)
                .padding(padding)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(spacing)) {
                ActivityNameTextField(activity, onChange, Modifier.fillMaxWidth())
                ActivityCurrentStatusDropdownField(activity, onChange, Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivityEditingPreview() {
    OngoingTheme {
        ActivityEditing(
            ContextualActivity.sample,
            onChange = { },
            onNavigationRequest = { },
            onDone = { }
        )
    }
}