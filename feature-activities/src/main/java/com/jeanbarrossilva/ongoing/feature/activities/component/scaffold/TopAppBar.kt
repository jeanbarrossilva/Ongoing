package com.jeanbarrossilva.ongoing.feature.activities.component.scaffold

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.activities.R
import com.jeanbarrossilva.ongoing.feature.activities.component.AvatarIcon
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarStyle
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
internal fun TopAppBar(
    user: User?,
    onAccountDetailsRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        TopAppBarStyle.Root,
        modifier,
        title = { Text(stringResource(R.string.feature_activities)) }
    ) {
        AvatarIcon(user, onClick = onAccountDetailsRequest)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TopAppBarPreview() {
    OngoingTheme {
        TopAppBar(user = null, onAccountDetailsRequest = { })
    }
}