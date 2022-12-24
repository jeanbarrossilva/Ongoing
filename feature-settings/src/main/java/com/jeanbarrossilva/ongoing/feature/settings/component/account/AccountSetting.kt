package com.jeanbarrossilva.ongoing.feature.settings.component.account

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.jeanbarrossilva.ongoing.context.user.ContextualUser
import com.jeanbarrossilva.ongoing.context.user.component.Avatar
import com.jeanbarrossilva.ongoing.feature.settings.R
import com.jeanbarrossilva.ongoing.feature.settings.component.account.component.AccountSettingNameAndEmail
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

internal const val ACCOUNT_SETTING_SIGN_OUT_BUTTON_TAG = "account_setting_sign_out_button"

@Composable
internal fun AccountSetting(
    user: ContextualUser,
    onSignOutRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .clickable(enabled = false) { }
            .padding(Size.Spacing.m)
    ) {
        SettingsMenuLink(
            title = { AccountSettingNameAndEmail(user, Modifier.offset(x = Size.Spacing.xl)) },
            icon = { Avatar(user.avatarUrl, Modifier.size(42.dp)) },
            action = {
                IconButton(
                    onClick = onSignOutRequest,
                    Modifier
                        .offset(x = Size.Spacing.xl)
                        .testTag(ACCOUNT_SETTING_SIGN_OUT_BUTTON_TAG),
                    colors = iconButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(
                        Icons.Rounded.ExitToApp,
                        contentDescription = stringResource(R.string.feature_settings_account_sign_out)
                    )
                }
            },
            onClick = { }
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun AccountSettingPreview() {
    OngoingTheme {
        AccountSetting(ContextualUser.sample, onSignOutRequest = { })
    }
}