package com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.component

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.platform.designsystem.R
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun NavigationButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick) {
        Icon(
            Icons.Rounded.Close,
            contentDescription = stringResource(
                R.string.platform_design_system_navigation_button_content_description
            )
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun NavigationButtonPreview() {
    OngoingTheme {
        NavigationButton(onClick = { })
    }
}