package com.jeanbarrossilva.ongoing.feature.account

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.account.component.PersonalInfo
import com.jeanbarrossilva.ongoing.feature.account.component.SignOutButton
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.Scaffold
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarStyle
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
fun Account(
    user: User,
    viewModel: AccountViewModel,
    onNavigationRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Account(
        user,
        onNavigationRequest,
        onLogOutRequest = {
            viewModel.logOut()
            onNavigationRequest()
        },
        modifier
    )
}

@Composable
internal fun Account(
    user: User,
    onNavigationRequest: () -> Unit,
    onLogOutRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
             TopAppBar(
                 TopAppBarStyle.Navigable(onNavigationRequest),
                 title = { Text(stringResource(R.string.feature_account)) }
             )
        },
        modifier
    ) {
        Background(
            Modifier
                .padding(Size.Spacing.xxxl)
                .padding(it)
        ) {
            PersonalInfo(user, Modifier.fillMaxWidth())

            SignOutButton(
                onClick = onLogOutRequest,
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun AccountPreview() {
    OngoingTheme {
        Account(User.sample, onNavigationRequest = { }, onLogOutRequest = { })
    }
}