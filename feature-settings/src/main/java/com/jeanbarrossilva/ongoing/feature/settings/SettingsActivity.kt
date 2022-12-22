package com.jeanbarrossilva.ongoing.feature.settings

import android.content.Context
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.user.User
import com.jeanbarrossilva.ongoing.feature.settings.app.AppNameProvider
import com.jeanbarrossilva.ongoing.feature.settings.app.CurrentVersionNameProvider
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.argumentOf
import com.jeanbarrossilva.ongoing.platform.extensions.startActivity
import org.koin.android.ext.android.inject

class SettingsActivity internal constructor(): ComposableActivity() {
    private val sessionManager by inject<SessionManager>()
    private val appNameProvider by inject<AppNameProvider>()
    private val currentVersionNameProvider by inject<CurrentVersionNameProvider>()
    private val activitiesFetcher by inject<ContextualActivitiesFetcher>()
    private val user by argumentOf<User>(USER_KEY)
    private val viewModel by viewModels<SettingsViewModel> {
        SettingsViewModel.createFactory(
            sessionManager,
            user,
            appNameProvider,
            currentVersionNameProvider,
            activitiesFetcher
        )
    }

    @Composable
    override fun Content() {
        Settings(user, viewModel, onNavigationRequest = onBackPressedDispatcher::onBackPressed)
    }

    companion object {
        private const val USER_KEY = "user"

        fun start(context: Context, user: User) {
            context.startActivity<SettingsActivity>(USER_KEY to user)
        }
    }
}