package com.jeanbarrossilva.ongoing.feature.activities.rule

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.user.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.feature.activities.Activities
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesBoundary
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesViewModel
import com.jeanbarrossilva.ongoing.feature.activities.inmemory.InMemoryActivitiesGateway
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import org.junit.rules.ExternalResource

internal class ActivitiesTestRule(
    private val platformRegistryRule: PlatformRegistryTestRule,
    private val composeRule: ComposeContentTestRule
): ExternalResource() {
    val userRepository = InMemoryUserRepository()

    private val activityRegistry
        get() = platformRegistryRule.activityRegistry
    private val sessionManager
        get() = platformRegistryRule.sessionManager

    lateinit var fetcher: ContextualActivitiesFetcher
        private set

    override fun before() {
        init()
        setContent()
    }

    private fun init() {
        fetcher = ContextualActivitiesFetcher(sessionManager, userRepository, activityRegistry)
    }

    private fun setContent() {
        val gateway = InMemoryActivitiesGateway(sessionManager, userRepository, fetcher)
        val viewModel = ActivitiesViewModel(gateway)
        composeRule.setContent { Activities(viewModel, ActivitiesBoundary.empty) }
    }
}