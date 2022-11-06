package com.jeanbarrossilva.ongoing.feature.account.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanbarrossilva.ongoing.context.registry.component.Avatar
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.ContentAlpha
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun PersonalInfo(user: User, modifier: Modifier = Modifier) {
    Column(
        modifier,
        Arrangement.spacedBy(Size.Spacing.xxxl),
        Alignment.CenterHorizontally
    ) {
        Avatar(user, Modifier.size(128.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(Size.Spacing.xs),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                user.name,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                user.email,
                Modifier.alpha(ContentAlpha.MEDIUM),
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PersonalInfoPreview() {
    OngoingTheme {
        PersonalInfo(User.sample)
    }
}