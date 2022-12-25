package com.jeanbarrossilva.ongoing.feature

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.core.user.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetails
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsViewModel
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class ActivityDetailsTests {
    private lateinit var activityId: String

    private val activityRegistry
        get() = platformRegistryRule.activityRegistry
    private val sessionManager
        get() = platformRegistryRule.sessionManager

    @get:Rule
    val platformRegistryRule = PlatformRegistryTestRule.create()

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        init()
        setContent()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun hideEditButtonWhenSignedOut() {
        runTest { sessionManager.session<Session.SignedIn>()?.end() }
        composeRule.onNodeWithTag(FloatingActionButton.TAG).assertDoesNotExist()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun init() {
        sessionManager.session<Session.SignedIn>()?.userId?.let {
            runTest {
                activityId = activityRegistry.register(ownerUserId = it, name = "Run")
            }
        }
    }

    private fun setContent() {
        val userRepository = InMemoryUserRepository()
        val fetcher = ContextualActivitiesFetcher(sessionManager, userRepository, activityRegistry)
        val viewModel = ActivityDetailsViewModel(
            sessionManager,
            activityRegistry,
            Observation.empty,
            fetcher,
            activityId
        )
        composeRule.setContent {
            ActivityDetails(
                ActivityDetailsBoundary.empty,
                viewModel,
                onObservationToggle = { },
                onNavigationRequest = { }
            )
        }
    }
}