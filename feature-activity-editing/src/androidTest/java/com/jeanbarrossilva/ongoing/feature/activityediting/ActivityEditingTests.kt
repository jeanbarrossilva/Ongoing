package com.jeanbarrossilva.ongoing.feature.activityediting

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualStatus
import com.jeanbarrossilva.ongoing.feature.activityediting.component.ConfirmationDialog
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.ActivityNameTextField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownMenuItem
import com.jeanbarrossilva.ongoing.feature.activityediting.extensions.pressBack
import com.jeanbarrossilva.ongoing.platform.registry.OngoingDatabase
import com.jeanbarrossilva.ongoing.platform.registry.extensions.activityRegistry
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class ActivityEditingTests {
    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()

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

    private fun setContent(mode: ActivityEditingMode, onNavigationRequest: () -> Unit = { }) {
        val database = OngoingDatabase.getInstance(context)
        val viewModel = ActivityEditingViewModel(database.activityRegistry, mode)
        composeRule.setContent {
            ActivityEditing(
                viewModel,
                onDone = { },
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
}