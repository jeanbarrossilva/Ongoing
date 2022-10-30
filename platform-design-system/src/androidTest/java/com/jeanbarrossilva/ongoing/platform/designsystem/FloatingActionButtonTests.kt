package com.jeanbarrossilva.ongoing.platform.designsystem

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButton
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.floatingactionbutton.FloatingActionButtonEnableability
import com.jeanbarrossilva.ongoing.platform.designsystem.extensions.assertIsEnabled
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class FloatingActionButtonTests {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun interactiveWhenEnabled() {
        assertInteractive(true, FloatingActionButtonEnableability.Enabled)
    }

    @Test
    fun interactiveWhenDisabledButInteractive() {
        assertInteractive(true, FloatingActionButtonEnableability.Disabled(isInteractive = true))
    }

    @Test
    fun notInteractiveWhenDisabled() {
        assertInteractive(false, FloatingActionButtonEnableability.Disabled())
    }

    private fun assertInteractive(
        expected: Boolean,
        enableability: FloatingActionButtonEnableability
    ) {
        var hasBeenInteractedWith = false
        composeRule.setContent {
            FloatingActionButton(
                enableability,
                onClick = { hasBeenInteractedWith = true },
                Modifier.testTag(TAG)
            ) {
            }
        }
        composeRule.onNodeWithTag(TAG).assertIsEnabled(enableability.isEnabled())
        composeRule.onNodeWithTag(TAG).performClick()
        assertEquals(expected, hasBeenInteractedWith)
    }

    companion object {
        private const val TAG = "fab"
    }
}