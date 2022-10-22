package com.jeanbarrossilva.ongoing.feature.activityediting.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.R
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.DropdownField
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun ActivityCurrentStatusDropdownField(
    activity: ContextualActivity?,
    onChange: (activity: ContextualActivity) -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    DropdownField(
        isExpanded,
        onExpansionToggle = { isExpanded = it },
        value = activity?.currentStatus?.title.orEmpty(),
        label = { Text(stringResource(R.string.feature_activity_editing_current_status)) },
        modifier
    ) { width ->
        activity?.statuses?.forEach { status ->
            DropdownMenuItem(
                text = { Text(status.title) },
                onClick = {
                    onChange(activity.copy(currentStatus = status))
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
        ActivityCurrentStatusDropdownField(ContextualActivity.sample, onChange = { })
    }
}