package com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.activitydetails.R
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold.FloatingActionButton.TAG
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButtonAvailability
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

object FloatingActionButton {
    const val TAG = "activity_details_fab"
}

@Composable
internal fun FloatingActionButton(
    isAvailable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick,
        modifier.testTag(TAG),
        FloatingActionButtonAvailability `for` isAvailable
    ) {
        Icon(
            Icons.Rounded.Edit,
            contentDescription =
                stringResource(R.string.feature_activity_details_fab_content_description)
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FloatingActionButtonPreview() {
    OngoingTheme {
        FloatingActionButton(isAvailable = true, onClick = { })
    }
}