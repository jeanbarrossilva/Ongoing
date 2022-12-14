package com.jeanbarrossilva.ongoing.feature.authentication.component.buttons

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.authentication.R
import com.jeanbarrossilva.ongoing.feature.authentication.component.buttons.DismissButton.TAG
import com.jeanbarrossilva.ongoing.platform.designsystem.component.button.Button
import com.jeanbarrossilva.ongoing.platform.designsystem.component.button.ButtonRelevance
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

object DismissButton {
    const val TAG = "dismiss_button"
}

@Composable
internal fun DismissButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick,
        modifier
            .fillMaxWidth()
            .testTag(TAG),
        ButtonRelevance.Secondary()
    ) {
        Text(stringResource(R.string.feature_authentication_dismiss))
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DismissButtonPreview() {
    OngoingTheme {
        DismissButton(onClick = { })
    }
}