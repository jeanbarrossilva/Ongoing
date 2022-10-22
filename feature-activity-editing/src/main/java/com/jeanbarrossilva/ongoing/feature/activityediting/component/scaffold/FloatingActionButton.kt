package com.jeanbarrossilva.ongoing.feature.activityediting.component.scaffold

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.activityediting.R
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun FloatingActionButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(onClick) {
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
private fun FloatingActionButtonPreview() {
    OngoingTheme {
        FloatingActionButton(onClick = { })
    }
}