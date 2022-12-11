package com.jeanbarrossilva.ongoing.feature.activities.component.scaffold

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.activities.R
import com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.FloatingActionButton.TAG
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme
import com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.FloatingActionButton as _FloatingActionButton

object FloatingActionButton {
    const val TAG = "activities_fab"
}

@Composable
internal fun FloatingActionButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(onClick, modifier.testTag(TAG)) {
        Icon(
            Icons.Rounded.Add,
            contentDescription =
            stringResource(R.string.feature_activities_fab_content_description)
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FloatingActionButtonPreview() {
    OngoingTheme {
        _FloatingActionButton(onClick = { })
    }
}