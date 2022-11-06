package com.jeanbarrossilva.ongoing.feature.activities.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.context.registry.component.Avatar
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.activities.R
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun AvatarIcon(user: User?, onClick: () -> Unit, modifier: Modifier = Modifier) {
    user?.let {
        Avatar(
            user,
            modifier
                .clip(Avatar.shape)
                .clickable(onClick = onClick)
                .padding(12.dp)
                .size(24.dp)
        )
    } ?: IconButton(onClick) {
        Icon(
            Icons.Rounded.AccountCircle,
            contentDescription = stringResource(
                R.string.feature_activities_top_app_bar_action_account_content_description
            )
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ExistentUserAvatarIconPreview() {
    OngoingTheme {
        AvatarIcon(User.sample, onClick = { })
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun NonexistentUserAvatarIconPreview() {
    OngoingTheme {
        AvatarIcon(user = null, onClick = { })
    }
}