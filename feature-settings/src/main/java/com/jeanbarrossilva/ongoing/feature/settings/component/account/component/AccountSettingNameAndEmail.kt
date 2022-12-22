package com.jeanbarrossilva.ongoing.feature.settings.component.account.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.core.user.User
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.BackgroundContentSizing
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.ContentAlpha
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

internal const val ACCOUNT_SETTING_NAME_TAG = "account_setting_name"
internal const val ACCOUNT_SETTING_EMAIL_TAG = "account_setting_email"

@Composable
internal fun AccountSettingNameAndEmail(user: User, modifier: Modifier = Modifier) {
    Column(modifier, Arrangement.spacedBy(Size.Spacing.xxs)) {
        Text(
            user.name,
            Modifier.testTag(ACCOUNT_SETTING_NAME_TAG),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall
        )

        Text(
            user.email,
            Modifier
                .alpha(ContentAlpha.MEDIUM)
                .testTag(ACCOUNT_SETTING_EMAIL_TAG),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun AccountSettingNameAndEmailPreview() {
    OngoingTheme {
        Background(contentSizing = BackgroundContentSizing.WRAP) {
            AccountSettingNameAndEmail(User.sample)
        }
    }
}