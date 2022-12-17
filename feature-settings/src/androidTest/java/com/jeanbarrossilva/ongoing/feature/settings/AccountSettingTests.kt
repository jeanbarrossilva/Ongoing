package com.jeanbarrossilva.ongoing.feature.settings

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import app.cash.turbine.test
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.settings.component.account.ACCOUNT_SETTING_SIGN_OUT_BUTTON_TAG
import com.jeanbarrossilva.ongoing.feature.settings.component.account.component.ACCOUNT_SETTING_EMAIL_TAG
import com.jeanbarrossilva.ongoing.feature.settings.component.account.component.ACCOUNT_SETTING_NAME_TAG
import com.jeanbarrossilva.ongoing.feature.settings.rule.SettingsTestRule
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

internal class AccountSettingTests {
    private val platformRegistryRule = PlatformRegistryTestRule.create()
    private val composeRule = createComposeRule()
    private val settingsRule = SettingsTestRule(platformRegistryRule, composeRule)

    private val session
        get() = platformRegistryRule.session

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
                .assertTextEquals(getCurrentUser().name)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun showUserEmail() {
        runTest {
            composeRule
                .onNodeWithTag(ACCOUNT_SETTING_EMAIL_TAG, useUnmergedTree = true)
                .assertTextEquals(getCurrentUser().email)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun logOut() {
        composeRule.onNodeWithTag(ACCOUNT_SETTING_SIGN_OUT_BUTTON_TAG).performClick()
        runTest {
            session.getUser().test { assertNull(awaitItem()) }

            // Has to log in again in order for the underlying ActivityRegistryRule to be able to
            // clear the activities when the test is finished with the current user's ID.
            session.logIn()
        }
    }

    private suspend fun getCurrentUser(): User {
        return session.getUser().filterNotNull().first()
    }
}