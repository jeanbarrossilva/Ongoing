package com.jeanbarrossilva.ongoing.feature.settings

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import app.cash.turbine.test
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.extensions.getActivities
import com.jeanbarrossilva.ongoing.context.registry.extensions.register
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.core.session.user.UserRepository
import com.jeanbarrossilva.ongoing.feature.settings.category.AccountSettingTests
import com.jeanbarrossilva.ongoing.feature.settings.category.ActivitiesSettingsTests
import com.jeanbarrossilva.ongoing.feature.settings.component.account.ACCOUNT_SETTING_SIGN_OUT_BUTTON_TAG
import com.jeanbarrossilva.ongoing.feature.settings.component.account.component.ACCOUNT_SETTING_EMAIL_TAG
import com.jeanbarrossilva.ongoing.feature.settings.component.account.component.ACCOUNT_SETTING_NAME_TAG
import com.jeanbarrossilva.ongoing.feature.settings.extensions.clearActivities
import com.jeanbarrossilva.ongoing.feature.settings.extensions.onClearActivitiesConfirmationButton
import com.jeanbarrossilva.ongoing.feature.settings.extensions.onClearActivitiesSetting
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.unwrap
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category

internal class SettingsTests {
    private lateinit var userRepository: UserRepository
    private lateinit var activitiesFetcher: ContextualActivitiesFetcher
    private lateinit var viewModel: SettingsViewModel

    private val activityRegistry
        get() = platformRegistryRule.activityRegistry
    private val session
        get() = platformRegistryRule.session

    @get:Rule
    val platformRegistryRule = PlatformRegistryTestRule.create()

    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        runTest {
            userRepository = InMemoryUserRepository(session)
            activitiesFetcher =
                ContextualActivitiesFetcher(session, userRepository, activityRegistry)
            viewModel = SettingsViewModel(
                session,
                getCurrentUser(),
                activitiesFetcher
            )
        }
        composeRule.setContent {
            Settings(runBlocking { getCurrentUser() }, viewModel, onNavigationRequest = { })
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Category(AccountSettingTests::class)
    fun showUserNameOnAccountSetting() {
        runTest {
            composeRule
                .onNodeWithTag(ACCOUNT_SETTING_NAME_TAG, useUnmergedTree = true)
                .assertTextEquals(getCurrentUser().name)
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Category(AccountSettingTests::class)
    fun showUserEmailOnAccountSetting() {
        runTest {
            composeRule
                .onNodeWithTag(ACCOUNT_SETTING_EMAIL_TAG, useUnmergedTree = true)
                .assertTextEquals(getCurrentUser().email)
        }
    }

    @Test
    @Category(ActivitiesSettingsTests::class)
    fun showClearActivitiesSettingWhenHasActivities() {
        registerActivities()
        composeRule.onClearActivitiesSetting().assertExists()
    }

    @Test
    @Category(ActivitiesSettingsTests::class)
    fun hideClearActivitiesSettingWhenHasNoActivities() {
        composeRule.onClearActivitiesSetting().assertDoesNotExist()
    }

    @Test
    @Category(ActivitiesSettingsTests::class)
    fun showConfirmationDialogOnActivityClearing() {
        registerActivities()
        composeRule.onClearActivitiesSetting().performClick()
        composeRule.onClearActivitiesConfirmationButton().assertExists()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Category(ActivitiesSettingsTests::class)
    fun clearActivities() {
        registerActivities()
        composeRule.clearActivities()
        runTest {
            activitiesFetcher.fetch()
            assertEquals(
                emptyList<Activity>(),
                platformRegistryRule.activityRegistry.getActivities()
            )
        }
    }

    @Test
    @Category(ActivitiesSettingsTests::class)
    fun hideClearActivitiesSettingAfterClearing() {
        registerActivities()
        composeRule.clearActivities()
        composeRule.waitUntil {
            runBlocking {
                activitiesFetcher.getActivities().unwrap().first().isEmpty()
            }
        }
        composeRule.onClearActivitiesSetting().assertDoesNotExist()
    }

    private suspend fun getCurrentUser(): User {
        return session.getUser().filterNotNull().first()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun registerActivities() {
        runTest {
            ContextualActivity
                .samples
                .forEach { activityRegistry.register(ownerUserId = getCurrentUser().id, it) }
            activitiesFetcher.fetch()
        }
    }
}