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
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus
import com.jeanbarrossilva.ongoing.feature.activityediting.component.ConfirmationDialog
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.ActivityNameTextField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownMenuItem
import com.jeanbarrossilva.ongoing.feature.activityediting.component.scaffold.FloatingActionButton
import com.jeanbarrossilva.ongoing.feature.activityediting.extensions.pressBack
import com.jeanbarrossilva.ongoing.platform.registry.test.database.OngoingDatabaseRule
import com.jeanbarrossilva.ongoing.platform.registry.test.extensions.activityRegistry
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class ActivityEditingTests {
    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    val databaseRule = OngoingDatabaseRule()

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

    @Test
    fun save() {
        var isDone = false
        setContent(ActivityEditingMode.Addition, onDone = { isDone = true })
        composeRule.onNodeWithTag(ActivityNameTextField.TAG).performTextInput("Name")
        composeRule.onNodeWithTag(ActivityStatusDropdownField.TAG).performClick()
        composeRule
            .onNodeWithTag(ActivityStatusDropdownMenuItem.getTag(ContextualStatus.ONGOING))
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

    private fun setContent(
        mode: ActivityEditingMode,
        onDone: () -> Unit = { },
        onNavigationRequest: () -> Unit = { }
    ) {
        val activityRegistry = databaseRule.getDatabase().activityRegistry
        val viewModel = ActivityEditingViewModel(activityRegistry, mode)
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
}