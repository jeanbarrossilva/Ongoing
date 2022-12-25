package com.jeanbarrossilva.ongoing.feature.activities

import android.content.Context
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.extensions.getActivities
import com.jeanbarrossilva.ongoing.context.registry.extensions.register
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.feature.activities.component.REMOVAL_CONFIRMATION_DIALOG_CONFIRMATION_BUTTON_TAG
import com.jeanbarrossilva.ongoing.feature.activities.component.REMOVAL_CONFIRMATION_DIALOG_TEXT_TAG
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.ActivityCard
import com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.topappbar.TOP_APP_BAR_SELECTION_ACTIONS_REMOVE_TAG
import com.jeanbarrossilva.ongoing.feature.activities.extensions.hasTestTagPrefixedWith
import com.jeanbarrossilva.ongoing.feature.activities.rule.ActivitiesTestRule
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TOP_APP_BAR_TAG
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.unwrap
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

internal class ActivitiesTests {private val platformRegistryRule = PlatformRegistryTestRule.create()
    private val composeRule = createComposeRule()
    private val activitiesRule = ActivitiesTestRule(platformRegistryRule, composeRule)

    private val activity
        get() = ContextualActivity.sample
    private val activityCard
        get() = composeRule.onNode(hasTestTagPrefixedWith(ActivityCard.TAG))
    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()
    private val fetcher
        get() = activitiesRule.fetcher
    private val removeButton
        get() = composeRule.onNodeWithTag(TOP_APP_BAR_SELECTION_ACTIONS_REMOVE_TAG)

    @get:Rule
    val ruleChain: RuleChain? =
        RuleChain.outerRule(platformRegistryRule).around(composeRule).around(activitiesRule)

    @Test
    fun enterSelectionModeOnActivityLongClick() {
        registerActivity()
        longClickFirstActivity()
        assertTopAppBarTitleEquals(
            context.resources.getQuantityString(R.plurals.feature_activities_selection, 1, 1)
        )
        assertTrue(activityCard.fetchSemanticsNode().config[SemanticsProperties.Selected])
    }

    @Test
    fun showConfirmationDialogWhenRemovingAnActivity() {
        registerActivity()
        longClickFirstActivity()
        removeButton.performClick()
        composeRule
            .onNodeWithTag(REMOVAL_CONFIRMATION_DIALOG_TEXT_TAG)
            .assertTextEquals(
                context.resources.getQuantityString(
                    R.plurals.feature_activities_removal_confirmation,
                    1,
                    activity.name
                )
            )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun removeActivity() {
        registerActivity()
        removeFirstActivity()
        runTest {
            assertEquals(emptyList<ContextualActivity>(), fetcher.getActivities().unwrap().first())
        }
    }

    @Test
    fun exitSelectionModeAfterRemovingAnActivity() {
        registerActivity()
        removeFirstActivity()
        assertTopAppBarTitleEquals(context.getString(R.string.feature_activities))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun navigateToAuthenticationWhenTryingToAddAnActivityWhileSignedOut() {
        var isAtAuthentication = false
        val boundary = ActivitiesBoundary.create { isAtAuthentication = true }
        activitiesRule.setBoundary(boundary)
        runTest { platformRegistryRule.sessionManager.session<Session.SignedIn>()?.end() }
        composeRule.onNodeWithTag(FloatingActionButton.TAG).performClick()
        assertTrue(isAtAuthentication)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun registerActivity() {
        runTest {
            fetcher.register(ContextualActivity.sample)
        }
    }

    private fun removeFirstActivity() {
        longClickFirstActivity()
        removeButton.performClick()
        composeRule.onNodeWithTag(REMOVAL_CONFIRMATION_DIALOG_CONFIRMATION_BUTTON_TAG)
            .performClick()
    }

    private fun longClickFirstActivity() {
        activityCard.performTouchInput {
            longClick(center)
        }
    }

    private fun assertTopAppBarTitleEquals(expected: String) {
        composeRule
            .onNodeWithTag(TOP_APP_BAR_TAG)
            .onChildren()
            .filterToOne(hasTextExactly(expected))
            .assertExists()
    }
}