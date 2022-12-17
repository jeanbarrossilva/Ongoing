package com.jeanbarrossilva.ongoing.feature.settings

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.extensions.getActivities
import com.jeanbarrossilva.ongoing.context.registry.extensions.register
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.session.user.User
import com.jeanbarrossilva.ongoing.feature.settings.extensions.clearActivities
import com.jeanbarrossilva.ongoing.feature.settings.extensions.onClearActivitiesConfirmationButton
import com.jeanbarrossilva.ongoing.feature.settings.extensions.onClearActivitiesSetting
import com.jeanbarrossilva.ongoing.feature.settings.rule.SettingsTestRule
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.unwrap
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

internal class ActivitiesSettingsTests {
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

    @Test
    fun showClearActivitiesSettingWhenHasActivities() {
        registerActivities()
        composeRule.onClearActivitiesSetting().assertExists()
    }

    @Test
    fun hideClearActivitiesSettingWhenHasNoActivities() {
        composeRule.onClearActivitiesSetting().assertDoesNotExist()
    }

    @Test
    fun showConfirmationDialogOnActivityClearing() {
        registerActivities()
        composeRule.onClearActivitiesSetting().performClick()
        composeRule.onClearActivitiesConfirmationButton().assertExists()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun clearActivities() {
        registerActivities()
        composeRule.clearActivities()
        runTest {
            settingsRule.activitiesFetcher.fetch()
            assertEquals(
                emptyList<Activity>(),
                platformRegistryRule.activityRegistry.getActivities()
            )
        }
    }

    @Test
    fun hideClearActivitiesSettingAfterClearing() {
        registerActivities()
        composeRule.clearActivities()
        composeRule.waitUntil {
            runBlocking {
                settingsRule.activitiesFetcher.getActivities().unwrap().first().isEmpty()
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
            ContextualActivity.samples.forEach {
                platformRegistryRule.activityRegistry.register(
                    ownerUserId = getCurrentUser().id,
                    it
                )
            }
            settingsRule.activitiesFetcher.fetch()
        }
    }
}