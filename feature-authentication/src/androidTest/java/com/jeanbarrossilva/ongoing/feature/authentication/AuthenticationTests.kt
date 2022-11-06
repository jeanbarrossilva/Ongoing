package com.jeanbarrossilva.ongoing.feature.authentication

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySessionManager
import com.jeanbarrossilva.ongoing.feature.authentication.component.buttons.DismissButton
import com.jeanbarrossilva.ongoing.feature.authentication.component.buttons.LogInButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class AuthenticationTests {
    private val sessionManager = InMemorySessionManager()
    private val viewModel = AuthenticationViewModel(sessionManager)

    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun logIn() {
        composeRule.setContent { Authentication(viewModel, onNavigationRequest = { }) }
        composeRule.onNodeWithTag(LogInButton.TAG).performClick()
        runTest { assertNotNull(sessionManager.getUser().first()) }
    }

    @Test
    fun dismiss() {
        var isClosed = false
        composeRule.setContent {
            Authentication(viewModel, onNavigationRequest = { isClosed = true })
        }
        composeRule.onNodeWithTag(DismissButton.TAG).performClick()
        assertTrue(isClosed)
    }
}