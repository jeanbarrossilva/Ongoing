package com.jeanbarrossilva.ongoing.feature.activityediting

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualStatus
import com.jeanbarrossilva.ongoing.feature.activityediting.component.ActivityCurrentStatusDropdownField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.ActivityNameTextField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.designsystem.component.Scaffold
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarRelevance
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.rememberTextFieldSubmitter
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun ActivityEditing(
    viewModel: ActivityEditingViewModel,
    onNavigationRequest: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val props by viewModel.props.collectAsState()

    ActivityEditing(
        props.name,
        onNameChange = {
            viewModel.updateProps {
                copy(name = it)
            }
        },
        props.currentStatus,
        onCurrentStatusChange = {
            viewModel.updateProps {
                copy(currentStatus = it)
            }
        },
        onNavigationRequest,
        onSaveRequest = {
            viewModel.save()
            onDone()
        },
        modifier
    )
}

@Composable
internal fun ActivityEditing(
    name: String,
    onNameChange: (name: String) -> Unit,
    currentStatus: ContextualStatus?,
    onCurrentStatusChange: (currentStatus: ContextualStatus) -> Unit,
    onNavigationRequest: () -> Unit,
    onSaveRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = Size.Spacing.xxl
    val nameSubmitter = rememberTextFieldSubmitter()
    val currentStatusSubmitter = rememberTextFieldSubmitter()
    var isValid by remember { mutableStateOf(ActivityEditingModel.isNameValid(name)) }

    Scaffold(
        topBar = {
            TopAppBar(TopAppBarRelevance.Subsequent(onNavigationRequest)) {
                Text(stringResource(R.string.feature_activity_editing))
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                isEnabled = isValid,
                onClick = {
                    if (isValid) {
                        onSaveRequest()
                    }
                },
                nameSubmitter,
                currentStatusSubmitter
            )
       },
        modifier
    ) { padding ->
        Background(
            Modifier
                .padding(spacing)
                .padding(padding)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(spacing)) {
                ActivityNameTextField(
                    name,
                    onChange = { newName, isNameValid ->
                        isValid = isValid && isNameValid
                        onNameChange(newName)
                    },
                    nameSubmitter,
                    Modifier.fillMaxWidth()
                )

                ActivityCurrentStatusDropdownField(
                    currentStatus,
                    onCurrentStatusChange,
                    currentStatusSubmitter,
                    Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FilledActivityEditingPreview() {
    OngoingTheme {
        ActivityEditing(
            ContextualActivity.sample.name,
            onNameChange = { },
            ContextualActivity.sample.currentStatus,
            onCurrentStatusChange = { },
            onNavigationRequest = { },
            onSaveRequest = { }
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun EmptyActivityEditingPreview() {
    OngoingTheme {
        ActivityEditing(
            name = "",
            onNameChange = { },
            currentStatus = null,
            onCurrentStatusChange = { },
            onNavigationRequest = { },
            onSaveRequest = { }
        )
    }
}