package com.jeanbarrossilva.ongoing.feature.settings

import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.settings.component.AccountSetting
import com.jeanbarrossilva.ongoing.feature.settings.component.activities.ActivitiesSettings
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.Scaffold
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarStyle
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.components
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
fun Settings(
    user: User,
    viewModel: SettingsViewModel,
    onNavigationRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasActivities by viewModel.hasActivities.collectAsState(initial = false)

    Settings(
        user,
        onNavigationRequest,
        hasActivities,
        onLogOut = {
            viewModel.logOut()
            onNavigationRequest()
        },
        onActivityClearingRequest = viewModel::clearActivities,
        modifier
    )
}

@Composable
internal fun Settings(
    user: User,
    onNavigationRequest: () -> Unit,
    hasActivities: Boolean,
    onLogOut: () -> Unit,
    onActivityClearingRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
             TopAppBar(
                 TopAppBarStyle.Navigable(onNavigationRequest),
                 title = { Text(stringResource(R.string.feature_settings)) }
             )
        },
        modifier
    ) {
        Background {
            LazyColumn(contentPadding = it.bars) {
                components {
                    add { AccountSetting(user, onLogOut) }
                    add { ActivitiesSettings(hasActivities, onActivityClearingRequest) }
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SettingsPreview() {
    OngoingTheme {
        Settings(
            User.sample,
            onNavigationRequest = { },
            hasActivities = true,
            onLogOut = { },
            onActivityClearingRequest = { }
        )
    }
}