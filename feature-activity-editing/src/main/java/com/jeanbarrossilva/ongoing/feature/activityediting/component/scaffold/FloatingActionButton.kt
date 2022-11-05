package com.jeanbarrossilva.ongoing.feature.activityediting.component.scaffold

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.activityediting.R
import com.jeanbarrossilva.ongoing.feature.activityediting.component.scaffold.FloatingActionButton.TAG
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.submitter.TextFieldSubmitter
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButtonEnableability
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

internal object FloatingActionButton {
    const val TAG = "activity_editing_fab"
}

@Suppress("NAME_SHADOWING")
@Composable
internal fun FloatingActionButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    vararg submitters: TextFieldSubmitter,
    modifier: Modifier = Modifier
) {
    val enableability = if (isEnabled) {
        FloatingActionButtonEnableability.Enabled
    } else {
        FloatingActionButtonEnableability.Disabled(isInteractive = true)
    }
    val onClick = {
        if (isEnabled) {
            onClick()
        }
    }

    FloatingActionButton(
        enableability,
        onClick = {
            submitters.forEach(TextFieldSubmitter::submit)
            onClick()
        },
        modifier.testTag(TAG)
    ) {
        Icon(
            Icons.Rounded.Done,
            contentDescription =
                stringResource(R.string.feature_activity_editing_fab_content_description)
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun EnabledFloatingActionButtonPreview() {
    OngoingTheme {
        FloatingActionButton(isEnabled = true, onClick = { })
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DisabledFloatingActionButtonPreview() {
    OngoingTheme {
        FloatingActionButton(isEnabled = false, onClick = { })
    }
}