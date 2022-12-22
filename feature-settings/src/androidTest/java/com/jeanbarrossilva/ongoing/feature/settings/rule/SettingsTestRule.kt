package com.jeanbarrossilva.ongoing.feature.settings.rule

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.settings.Settings
import com.jeanbarrossilva.ongoing.feature.settings.SettingsViewModel
import com.jeanbarrossilva.ongoing.feature.settings.app.AppNameProvider
import com.jeanbarrossilva.ongoing.feature.settings.app.CurrentVersionNameProvider
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.runBlocking
import org.junit.rules.ExternalResource

internal class SettingsTestRule(
    private val platformRegistryRule: PlatformRegistryTestRule,
    private val composeRule: ComposeContentTestRule
): ExternalResource() {
    private lateinit var userRepository: UserRepository

    lateinit var activitiesFetcher: ContextualActivitiesFetcher
        private set
    lateinit var viewModel: SettingsViewModel
        private set

    private val sessionManager
        get() = platformRegistryRule.sessionManager

    override fun before() {
        prepare()
        setContent()
    }

    private fun prepare() {
        userRepository = InMemoryUserRepository()
        activitiesFetcher = ContextualActivitiesFetcher(
            sessionManager,
            userRepository,
            platformRegistryRule.activityRegistry
        )
        viewModel = SettingsViewModel(
            sessionManager,
            runBlocking { getCurrentUser() },
            AppNameProvider.sample,
            CurrentVersionNameProvider.sample,
            activitiesFetcher
        )
    }

    private fun setContent() {
        composeRule.setContent {
            Settings(
                runBlocking { getCurrentUser() },
                viewModel,
                onNavigationRequest = { }
            )
        }
    }

    private suspend fun getCurrentUser(): User {
        val currentUserId = requireNotNull(sessionManager.session<Session.SignedIn>()?.userId)
        return requireNotNull(userRepository.getUserById(currentUserId))
    }
}