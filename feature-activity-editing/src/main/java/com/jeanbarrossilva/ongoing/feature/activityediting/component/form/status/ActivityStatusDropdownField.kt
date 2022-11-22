package com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status

import android.content.res.Configuration
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus
import com.jeanbarrossilva.ongoing.context.registry.extensions.title
import com.jeanbarrossilva.ongoing.feature.activityediting.R
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownField.TAG
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.DropdownField
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.TextFieldRule
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.submitter.TextFieldSubmitter
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.rememberTextFieldSubmitter
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

object ActivityStatusDropdownField {
    const val TAG = "activity_current_status_dropdown_field"
}

@Composable
internal fun ActivityStatusDropdownField(
    currentStatus: ContextualStatus?,
    onChange: (currentStatus: ContextualStatus) -> Unit,
    submitter: TextFieldSubmitter,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    DropdownField(
        isExpanded,
        onExpansionToggle = { isExpanded = it },
        value = currentStatus?.title.orEmpty(),
        label = { Text(stringResource(R.string.feature_activity_editing_current_status)) },
        modifier.testTag(TAG),
        rules = listOf(
            TextFieldRule(
                stringResource(R.string.feature_activity_editing_error_blank_field),
                String::isNotEmpty
            )
        ),
        submitter
    ) { width ->
        ContextualStatus.values.forEach { status ->
            ActivityStatusDropdownMenuItem(
                status,
                onClick = {
                    onChange(status)
                    isExpanded = false
                },
                Modifier.width(width)
            )
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivityCurrentStatusDropdownFieldPreview() {
    OngoingTheme {
        ActivityStatusDropdownField(
            ContextualActivity.sample.status,
            onChange = { },
            rememberTextFieldSubmitter()
        )
    }
}