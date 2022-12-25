package com.jeanbarrossilva.ongoing.feature

import android.content.Context
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.inmemory.InMemoryActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySessionManager
import com.jeanbarrossilva.ongoing.core.user.UserRepository
import com.jeanbarrossilva.ongoing.core.user.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsBoundary
import com.jeanbarrossilva.ongoing.feature.activitydetails.activityDetailsModule
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.extensions.Intent
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.koin.dsl.module
import org.koin.test.KoinTestRule

internal class ActivityDetailsTests {
    private val coreModule = module {
        single<SessionManager> { InMemorySessionManager() }
        single<UserRepository> { InMemoryUserRepository() }
        single<ActivityRegistry> { InMemoryActivityRegistry() }
        single {
            ContextualActivitiesFetcher(
                sessionManager = get(),
                userRepository = get(),
                activityRegistry = get()
            )
        }
        single { ActivityDetailsBoundary.empty }
    }
    private val platformRegistryRule = PlatformRegistryTestRule.create()
    private val koinRule = KoinTestRule.create { modules(coreModule, activityDetailsModule) }
    private val composeRule = createEmptyComposeRule()

    @get:Rule
    val ruleChain: RuleChain? =
        RuleChain.outerRule(platformRegistryRule).around(koinRule).around(composeRule)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        runTest {
            registerActivity()?.let {
                launchAndroidActivity(it)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun hideEditButtonWhenSignedOut() {
        runTest { platformRegistryRule.sessionManager.session<Session.SignedIn>()?.end() }
        composeRule.onNodeWithTag(FloatingActionButton.TAG).assertDoesNotExist()
    }

    private suspend fun registerActivity(): String? {
        return with(platformRegistryRule) {
            sessionManager.session<Session.SignedIn>()?.userId?.let {
                activityRegistry.register(ownerUserId = it, name = "Run")
            }
        }
    }

    private fun launchAndroidActivity(activityId: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = Intent<ActivityDetailsActivity>(
            context, ActivityDetailsActivity.ACTIVITY_ID_KEY to activityId
        )
        ActivityScenario.launch<ActivityDetailsActivity>(intent)
    }
}