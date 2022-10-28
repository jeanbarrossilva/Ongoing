package com.jeanbarrossilva.ongoing.feature.activityediting.component

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingModel
import com.jeanbarrossilva.ongoing.feature.activityediting.R
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.TextField
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.TextFieldEnableability
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.TextFieldRule
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun ActivityNameTextField(
    name: String,
    onChange: (name: String, isValid: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        name,
        onChange,
        label = { Text(stringResource(R.string.feature_activity_editing_name)) },
        modifier,
        TextFieldEnableability.Enabled(
            rules = listOf(
                TextFieldRule(
                    stringResource(R.string.feature_activity_editing_name_error_blank),
                    ActivityEditingModel::isNameValid
                )
            )
        )
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivityNameTextFieldPreview() {
    OngoingTheme {
        ActivityNameTextField(ContextualActivity.sample.name, onChange = { _, _ -> })
    }
}