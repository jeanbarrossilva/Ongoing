package com.jeanbarrossilva.ongoing.feature.settings

import android.content.Context
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.platform.designsystem.core.composable.ComposableActivity
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.argumentOf
import com.jeanbarrossilva.ongoing.platform.extensions.startActivity
import org.koin.android.ext.android.inject

class SettingsActivity internal constructor(): ComposableActivity() {
    private val session by inject<Session>()
    private val activityRegistry by inject<ActivityRegistry>()
    private val activitiesFetcher by inject<ContextualActivitiesFetcher>()
    private val user by argumentOf<User>(USER_KEY)
    private val viewModel by viewModels<SettingsViewModel> {
        SettingsViewModel.createFactory(session, user, activitiesFetcher)
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