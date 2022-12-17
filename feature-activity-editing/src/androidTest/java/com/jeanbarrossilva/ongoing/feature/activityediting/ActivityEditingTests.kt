package com.jeanbarrossilva.ongoing.feature.activityediting

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.feature.activityediting.component.ConfirmationDialog
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.ActivityNameTextField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownMenuItem
import com.jeanbarrossilva.ongoing.feature.activityediting.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.feature.activityediting.extensions.pressBack
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class ActivityEditingTests {

    @get:Rule
    val platformRegistryRule = PlatformRegistryTestRule.create()

    @Suppress("UNCHECKED_CAST")
    @get:Rule
    val composeRule =
        createComposeRule() as AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>

    @Test
    fun exitOnBackPressWhenAddingWithoutChanges() {
        var isClosed = false
        setContent(ActivityEditingMode.Addition) { isClosed = true }
        assertConfirmationDialogDisplayedOnBackPress(false)
        assertTrue(isClosed)
    }

    @Test
    fun exitOnBackPressWhenModifyingWithoutChanges() {
        var isClosed = false
        setContent(ActivityEditingMode.Modification.sample) { isClosed = true }
        assertConfirmationDialogDisplayedOnBackPress(false)
        assertTrue(isClosed)
    }

    @Test
    fun showConfirmationDialogOnBackPressWhenAddingWithChanges() {
        setContent(ActivityEditingMode.Addition)
        composeRule.onNodeWithTag(ActivityStatusDropdownField.TAG).performClick()
        composeRule
            .onNodeWithTag(ActivityStatusDropdownMenuItem.getTag(ContextualStatus.DONE))
            .performClick()
        assertConfirmationDialogDisplayedOnBackPress(true)
    }

    @Test
    fun showConfirmationDialogOnBackPressWhenModifyingWithChanges() {
        setContent(ActivityEditingMode.Modification.sample)
        composeRule.onNodeWithTag(ActivityNameTextField.TAG).performTextInput(" :)")
        assertConfirmationDialogDisplayedOnBackPress(true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() {
        val name = "Rent an apartment"
        val status = ContextualStatus.DONE
        setContent(ActivityEditingMode.Addition)
        composeRule.onNodeWithTag(ActivityNameTextField.TAG).performTextReplacement(name)
        composeRule.onNodeWithTag(ActivityStatusDropdownField.TAG).performClick()
        composeRule.onNodeWithTag(ActivityStatusDropdownMenuItem.getTag(status)).performClick()
        composeRule.onNodeWithTag(FloatingActionButton.TAG).assertIsEnabled()
        composeRule.onNodeWithTag(FloatingActionButton.TAG).performClick()
        runTest {
            assertEquals(name, getLastActivity()?.name)
            assertEquals(status.toStatus(), getLastActivity()?.status)
        }
    }

    private fun setContent(
        mode: ActivityEditingMode,
        onDone: () -> Unit = { },
        onNavigationRequest: () -> Unit = { }
    ) {
        val viewModel = ActivityEditingViewModel(
            platformRegistryRule.session,
            platformRegistryRule.activityRegistry,
            mode
        )
        composeRule.setContent {
            ActivityEditing(
                viewModel,
                onDone,
                onNavigationRequest = { onNavigationRequest() }
            )
        }
    }

    private fun assertConfirmationDialogDisplayedOnBackPress(isDisplayed: Boolean) {
        composeRule.waitForIdle()
        composeRule.pressBack()
        composeRule
            .onNodeWithTag(ConfirmationDialog.TAG)
            .run { if (isDisplayed) assertIsDisplayed() else assertDoesNotExist() }
    }

    private suspend fun getLastActivity(): Activity? {
        return platformRegistryRule.activityRegistry.getActivities().lastOrNull()
    }
}