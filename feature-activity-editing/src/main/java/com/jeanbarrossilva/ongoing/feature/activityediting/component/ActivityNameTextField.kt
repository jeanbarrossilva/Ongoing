package com.jeanbarrossilva.ongoing.feature.activityediting.component

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.R
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ActivityNameTextField(
    activity: ContextualActivity?,
    onChange: (activity: ContextualActivity) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        activity?.name.orEmpty(),
        onValueChange = { activity?.copy(name = it)?.let(onChange) },
        modifier,
        label = { Text(stringResource(R.string.feature_activity_editing_name)) }
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivityNameTextFieldPreview() {
    OngoingTheme {
        ActivityNameTextField(ContextualActivity.sample, onChange = { })
    }
}