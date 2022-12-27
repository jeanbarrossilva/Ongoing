package com.jeanbarrossilva.ongoing.feature.activityediting

import android.content.res.Configuration
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.activityediting.component.ConfirmationDialog
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.ActivityNameTextField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingStatus
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.Scaffold
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarStyle
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.rememberTextFieldSubmitter
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.map
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toSerializableList
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.valueOrNull
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList

@Suppress("NAME_SHADOWING")
@Composable
fun ActivityEditing(
    viewModel: ActivityEditingViewModel,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigationRequest: (OnBackPressedDispatcher?) -> Unit = { it?.onBackPressed() }
) {
    val onBackPressedDispatcherOwner = LocalOnBackPressedDispatcherOwner.current
    val onBackPressedDispatcher = remember(onBackPressedDispatcherOwner) {
        onBackPressedDispatcherOwner?.onBackPressedDispatcher
    }
    val activity by viewModel.getActivity().collectAsState()
    val isChanged by viewModel.isChanged.collectAsState()
    val isValid by viewModel.isValid.collectAsState()
    val availableStatuses by viewModel.availableStatuses.collectAsState()
    var isConfirmationDialogVisible by remember { mutableStateOf(false) }
    val onNavigationRequest = {
        isConfirmationDialogVisible = false
        onNavigationRequest(onBackPressedDispatcher)
    }
    val onBackPressedCallback = remember(isChanged) {
        object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isChanged) {
                    isConfirmationDialogVisible = true
                } else {
                    remove()
                    onNavigationRequest()
                }
            }
        }
    }

    DisposableEffect(isChanged) {
        onBackPressedDispatcher?.addCallback(onBackPressedCallback)
        onDispose { onBackPressedCallback.remove() }
    }

    if (isConfirmationDialogVisible) {
        ConfirmationDialog(
            onConfirmationRequest = onNavigationRequest,
            onDismissRequest = { isConfirmationDialogVisible = false }
        )
    }

    ActivityEditing(
        activity.map(EditingActivity::name),
        onNameChange = viewModel::setName,
        availableStatuses,
        activity.map(EditingActivity::status),
        onCurrentStatusChange = viewModel::setStatus,
        isValid,
        onNavigationRequest = onNavigationRequest,
        onSaveRequest = {
            viewModel.edit()
            onBackPressedCallback.remove()
            onDone()
        },
        modifier
    )
}

@Composable
internal fun ActivityEditing(
    name: Loadable<String>,
    onNameChange: (name: String) -> Unit,
    availableStatuses: Loadable<SerializableList<EditingStatus>>,
    currentStatus: Loadable<EditingStatus>,
    onCurrentStatusChange: (status: EditingStatus) -> Unit,
    isValid: Boolean,
    onNavigationRequest: () -> Unit,
    onSaveRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = Size.Spacing.xxl
    val nameSubmitter = rememberTextFieldSubmitter()
    val currentStatusSubmitter = rememberTextFieldSubmitter()

    Scaffold(
        topBar = {
            TopAppBar(
                TopAppBarStyle.Navigable(onNavigationRequest),
                title = { Text(stringResource(R.string.feature_activity_editing)) }
            )
        },
        modifier,
        floatingActionButton = {
            FloatingActionButton(
                isEnabled = isValid,
                onClick = onSaveRequest,
                nameSubmitter,
                currentStatusSubmitter
            )
       },
    ) { padding ->
        Background(
            Modifier
                .padding(spacing)
                .padding(padding)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(spacing)) {
                ActivityNameTextField(
                    name.valueOrNull.orEmpty(),
                    onNameChange,
                    nameSubmitter,
                    isValid,
                    Modifier.fillMaxWidth()
                )

                ActivityStatusDropdownField(
                    availableStatuses,
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
private fun ActivityEditingPreview() {
    OngoingTheme {
        ActivityEditing(
            Loadable.Loaded(EditingActivity.sample.name),
            onNameChange = { },
            availableStatuses = Loadable.Loaded(EditingStatus.samples.toSerializableList()),
            Loadable.Loaded(EditingStatus.sample),
            onCurrentStatusChange = { },
            isValid = true,
            onNavigationRequest = { },
            onSaveRequest = { }
        )
    }
}