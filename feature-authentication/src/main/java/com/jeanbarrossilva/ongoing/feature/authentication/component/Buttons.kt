package com.jeanbarrossilva.ongoing.feature.authentication.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.authentication.component.buttons.DismissButton
import com.jeanbarrossilva.ongoing.feature.authentication.component.buttons.LogInButton
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun Buttons(
    onLogInRequest: () -> Unit,
    onDismissalRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier, Arrangement.spacedBy(Size.Spacing.s)) {
        LogInButton(onClick = onLogInRequest)
        DismissButton(onClick = onDismissalRequest)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ButtonsPreview() {
    OngoingTheme {
        Buttons(onLogInRequest = { }, onDismissalRequest = { })
    }
}