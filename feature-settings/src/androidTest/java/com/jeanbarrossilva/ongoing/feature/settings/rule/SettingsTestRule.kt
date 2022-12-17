package com.jeanbarrossilva.ongoing.feature.settings.rule

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.settings.Settings
import com.jeanbarrossilva.ongoing.feature.settings.SettingsViewModel
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
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

    private val session
        get() = platformRegistryRule.session

    override fun before() {
        prepare()
        setContent()
    }

    private fun prepare() {
        userRepository = InMemoryUserRepository(session)
        activitiesFetcher = ContextualActivitiesFetcher(
            session,
            userRepository,
            platformRegistryRule.activityRegistry
        )
        viewModel = SettingsViewModel(session, runBlocking { getCurrentUser() }, activitiesFetcher)
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
        return session.getUser().filterNotNull().first()
    }
}