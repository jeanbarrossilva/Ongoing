package com.jeanbarrossilva.ongoing.feature.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanbarrossilva.ongoing.context.user.ContextualUser
import com.jeanbarrossilva.ongoing.feature.settings.app.AppNameProvider
import com.jeanbarrossilva.ongoing.feature.settings.app.CurrentVersionNameProvider
import com.jeanbarrossilva.ongoing.feature.settings.component.About
import com.jeanbarrossilva.ongoing.feature.settings.component.account.AccountSetting
import com.jeanbarrossilva.ongoing.feature.settings.component.activities.ActivitiesSettings
import com.jeanbarrossilva.ongoing.platform.designsystem.component.background.Background
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.Scaffold
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBar
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TopAppBarStyle
import com.jeanbarrossilva.ongoing.platform.designsystem.configuration.Size
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.components
import com.jeanbarrossilva.ongoing.platform.designsystem.theme.OngoingTheme

@Composable
fun Settings(
    user: ContextualUser,
    viewModel: SettingsViewModel,
    onNavigationRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasActivities by viewModel.hasActivities.collectAsState(initial = false)

    Settings(
        user,
        viewModel.appName,
        viewModel.currentVersionName,
        hasActivities,
        onNavigationRequest,
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
    user: ContextualUser,
    appName: String,
    currentVersionName: String,
    hasActivities: Boolean,
    onNavigationRequest: () -> Unit,
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
                    add {
                        Spacer(Modifier.height(Size.Spacing.xxxxxxxxxxxxl))
                        About(appName, currentVersionName)
                    }
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
            ContextualUser.sample,
            AppNameProvider.sample.provide(),
            CurrentVersionNameProvider.sample.provide(),
            hasActivities = true,
            onNavigationRequest = { },
            onLogOut = { },
            onActivityClearingRequest = { }
        )
    }
}