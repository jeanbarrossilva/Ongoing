package com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status

import android.content.res.Configuration
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualStatus
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownMenuItem.getTag
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

internal object ActivityStatusDropdownMenuItem {
    fun getTag(status: ContextualStatus): String {
        return "activity_status_dropdown_menu_item_${status.name.lowercase()}"
    }
}

@Composable
internal fun ActivityStatusDropdownMenuItem(
    status: ContextualStatus,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        text = { Text(status.title) },
        onClick,
        modifier.testTag(getTag(status))
    )
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ActivityStatusDropdownItemPreview() {
    OngoingTheme {
        ActivityStatusDropdownMenuItem(ContextualStatus.TO_DO, onClick = { })
    }
}