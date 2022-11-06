package com.jeanbarrossilva.ongoing.feature.account.component

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.feature.account.R
import com.jeanbarrossilva.ongoing.platform.designsystem.component.button.Button
import com.jeanbarrossilva.ongoing.platform.designsystem.component.button.ButtonPriority
import com.jeanbarrossilva.ongoing.platform.designsystem.component.button.ButtonRelevance
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun SignOutButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick,
        modifier,
        ButtonRelevance.Primary(ButtonPriority.IMPORTANT),
        icon = {
            Icon(
                Icons.Rounded.ExitToApp,
                contentDescription = stringResource(R.string.feature_account_sign_out)
            )
        }
    ) {
        Text(stringResource(R.string.feature_account_sign_out))
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SignOutButtonPreview() {
    OngoingTheme {
        SignOutButton(onClick = { })
    }
}