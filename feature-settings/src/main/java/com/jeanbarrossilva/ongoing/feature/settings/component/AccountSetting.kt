package com.jeanbarrossilva.ongoing.feature.settings.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.jeanbarrossilva.ongoing.context.registry.component.Avatar
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.settings.R
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun AccountSetting(
    user: User,
    onSignOutRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .clickable(enabled = false) { }
            .padding(Size.Spacing.m)
    ) {
        SettingsMenuLink(
            title = {
                Text(
                    user.name,
                    Modifier.offset(x = Size.Spacing.m),
                    style = MaterialTheme.typography.titleSmall
                )
            },
            icon = { Avatar(user) },
            action = {
                IconButton(
                    onClick = onSignOutRequest,
                    Modifier.offset(x = Size.Spacing.xl),
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
        AccountSetting(User.sample, onSignOutRequest = { })
    }
}