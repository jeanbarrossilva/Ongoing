package com.jeanbarrossilva.ongoing.feature.activityediting

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.jeanbarrossilva.ongoing.feature.activityediting.component.ConfirmationDialog
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.ActivityNameTextField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownMenuItem
import com.jeanbarrossilva.ongoing.feature.activityediting.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingStatus
import com.jeanbarrossilva.ongoing.feature.activityediting.extensions.pressBack
import com.jeanbarrossilva.ongoing.feature.activityediting.rule.ActivityEditingTestRule
import com.jeanbarrossilva.ongoing.feature.activityediting.rule.EditingModeProvider
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

internal class ActivityEditingTests {
    private val platformRegistryTestRule = PlatformRegistryTestRule.create()

    @Suppress("UNCHECKED_CAST")
    private val composeRule =
        createComposeRule() as AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>

    private val activityEditingRule = ActivityEditingTestRule(platformRegistryTestRule, composeRule)

    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    val ruleChain: RuleChain? = RuleChain
        .outerRule(platformRegistryTestRule)
        .around(composeRule)
        .around(activityEditingRule)

    @Test
    fun exitOnBackPressWhenAddingWithoutChanges() {
        var isClosed = false
        activityEditingRule.setContent(EditingModeProvider.ADDITION) { isClosed = true }
        assertConfirmationDialogDisplayedOnBackPress(false)
        assertTrue(isClosed)
    }

    @Test
    fun exitOnBackPressWhenModifyingWithoutChanges() {
        var isClosed = false
        activityEditingRule.setContent(EditingModeProvider.MODIFICATION) { isClosed = true }
        assertConfirmationDialogDisplayedOnBackPress(false)
        assertTrue(isClosed)
    }

    @Test
    fun showConfirmationDialogOnBackPressWhenAddingWithChanges() {
        activityEditingRule.setContent(EditingModeProvider.ADDITION)
        composeRule.onNodeWithTag(ActivityStatusDropdownField.TAG).performClick()
        composeRule
            .onNodeWithTag(ActivityStatusDropdownMenuItem.getTag(EditingStatus.ongoing))
            .performClick()
        assertConfirmationDialogDisplayedOnBackPress(true)
    }

    @Test
    fun showConfirmationDialogOnBackPressWhenModifyingWithChanges() {
        activityEditingRule.setContent(EditingModeProvider.MODIFICATION)
        composeRule.onNodeWithTag(ActivityNameTextField.TAG).performTextInput(" :)")
        assertConfirmationDialogDisplayedOnBackPress(true)
    }

    @Test
    fun save() {
        var isDone = false
        activityEditingRule.setContent(EditingModeProvider.ADDITION, onDone = { isDone = true })
        composeRule.onNodeWithTag(ActivityNameTextField.TAG).performTextInput("Name")
        composeRule.onNodeWithTag(ActivityStatusDropdownField.TAG).performClick()
        composeRule
            .onNodeWithTag(ActivityStatusDropdownMenuItem.getTag(EditingStatus.ongoing))
            .performClick()
        composeRule
            .onAllNodesWithText(
                context.getString(R.string.feature_activity_editing_error_blank_field)
            )
            .assertCountEquals(0)
        composeRule.onNodeWithTag(FloatingActionButton.TAG).assertIsEnabled()
        composeRule.onNodeWithTag(FloatingActionButton.TAG).performClick()
        assertTrue(isDone)
    }

    private fun assertConfirmationDialogDisplayedOnBackPress(isDisplayed: Boolean) {
        composeRule.waitForIdle()
        composeRule.pressBack()
        composeRule
            .onNodeWithTag(ConfirmationDialog.TAG)
            .run { if (isDisplayed) assertIsDisplayed() else assertDoesNotExist() }
    }
}