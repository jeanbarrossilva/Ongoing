package com.jeanbarrossilva.ongoing.feature.settings

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.settings.component.account.ACCOUNT_SETTING_SIGN_OUT_BUTTON_TAG
import com.jeanbarrossilva.ongoing.feature.settings.component.account.component.ACCOUNT_SETTING_EMAIL_TAG
import com.jeanbarrossilva.ongoing.feature.settings.component.account.component.ACCOUNT_SETTING_NAME_TAG
import com.jeanbarrossilva.ongoing.feature.settings.extensions.arrayOfNotNull
import com.jeanbarrossilva.ongoing.feature.settings.rule.SettingsTestRule
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

internal class AccountSettingTests {
    private val userRepository = InMemoryUserRepository()
    private val platformRegistryRule = PlatformRegistryTestRule.create()
    private val composeRule = createComposeRule()
    private val settingsRule = SettingsTestRule(platformRegistryRule, composeRule)

    private val sessionManager
        get() = platformRegistryRule.sessionManager

    @get:Rule
    val ruleChain: RuleChain? = RuleChain
        .outerRule(platformRegistryRule)
        .around(composeRule)
        .around(settingsRule)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun showUserName() {
        runTest {
            composeRule
                .onNodeWithTag(ACCOUNT_SETTING_NAME_TAG, useUnmergedTree = true)
                .assertTextEquals(*arrayOfNotNull(getCurrentUser()?.name))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun showUserEmail() {
        runTest {
            composeRule
                .onNodeWithTag(ACCOUNT_SETTING_EMAIL_TAG, useUnmergedTree = true)
                .assertTextEquals(*arrayOfNotNull(getCurrentUser()?.email))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun logOut() {
        composeRule.onNodeWithTag(ACCOUNT_SETTING_SIGN_OUT_BUTTON_TAG).performClick()
        runTest {
            assertThat(sessionManager.session(), `is`(instanceOf(Session.SignedOut::class.java)))
        }
    }

    private suspend fun getCurrentUser(): User? {
        return sessionManager.session<Session.SignedIn>()?.userId?.let {
            userRepository.getUserById(it)
        }
    }
}