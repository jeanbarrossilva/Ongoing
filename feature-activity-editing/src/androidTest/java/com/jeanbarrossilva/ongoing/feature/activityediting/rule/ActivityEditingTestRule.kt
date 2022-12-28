package com.jeanbarrossilva.ongoing.feature.activityediting.rule

import android.content.Context
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditing
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingViewModel
import com.jeanbarrossilva.ongoing.feature.activityediting.infra.inmemory.InMemoryActivityEditingGateway
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.rules.ExternalResource

internal class ActivityEditingTestRule(
    private val platformRegistryRule: PlatformRegistryTestRule,
    private val composeRule: ComposeContentTestRule
): ExternalResource() {
    private lateinit var activityId: String

    private val activityRegistry
        get() = platformRegistryRule.activityRegistry
    private val sessionManager
        get() = platformRegistryRule.sessionManager

    override fun before() {
        registerActivity()
    }

    fun setContent(
        modeProvider: EditingModeProvider,
        onDone: () -> Unit = { },
        onNavigationRequest: () -> Unit = { }
    ) {
        val mode = modeProvider.provide(activityRegistry, activityId)
        val context = ApplicationProvider.getApplicationContext<Context>()
        val gateway = InMemoryActivityEditingGateway(sessionManager, mode, context)
        val viewModel = ActivityEditingViewModel(gateway)
        composeRule.setContent {
            ActivityEditing(viewModel, onDone, onNavigationRequest = { onNavigationRequest() })
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun registerActivity() {
        sessionManager.session<Session.SignedIn>()?.userId?.let {
            runTest {
                activityId = activityRegistry.register(ownerUserId = it, name = "Buy new clothes")
            }
        }
    }
}