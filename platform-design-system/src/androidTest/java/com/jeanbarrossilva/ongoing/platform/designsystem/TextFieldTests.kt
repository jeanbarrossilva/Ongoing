package com.jeanbarrossilva.ongoing.platform.designsystem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.TextField
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.TextFieldEnableability
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.TextFieldRule
import com.jeanbarrossilva.ongoing.platform.designsystem.component.input.textfield.submitter.TextFieldSubmitter
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class TextFieldTests {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun triggerValueChangeCallback() {
        var value by mutableStateOf("")
        val finalValue = "Hello!"
        composeRule.setContent {
            TextField(
                value,
                onValueChange = { newValue, _ -> value = newValue },
                Modifier.testTag(TAG)
            )
        }
        composeRule.onNodeWithTag(TAG).performTextInput(finalValue)
        composeRule.onNodeWithTag(TAG).assertTextEquals(finalValue)
    }

    @Test
    fun passTrueValidityToValueChangeCallbackWhenValid() {
        assertValid(true, "Input") {
            true
        }
    }

    @Test
    fun passFalseValidityToValueChangeCallbackWhenInvalid() {
        val invalidValue = "!"
        assertValid(false, invalidValue) { it != invalidValue }
    }

    @Test
    fun showErrorForInvalidValueOnSubmission() {
        val submitter = TextFieldSubmitter()
        val errorMessage = "Error!"
        composeRule.setContent {
            TextField(
                value = "",
                onValueChange = { _, _ -> },
                enableability = TextFieldEnableability.Enabled(
                    rules = listOf(TextFieldRule(errorMessage, String::isNotBlank)),
                    submitter = submitter
                )
            )
        }
        submitter.submit()
        composeRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    private fun assertValid(
        expected: Boolean,
        input: String,
        callback: (value: String) -> Boolean
    ) {
        var value by mutableStateOf("")
        var isValid by mutableStateOf<Boolean?>(null)
        composeRule.setContent {
            TextField(
                value,
                onValueChange = { newValue, isValueValid ->
                    value = newValue
                    isValid = isValueValid
                },
                Modifier.testTag(TAG),
                TextFieldEnableability.Enabled(rules = listOf(TextFieldRule("", callback)))
            )
        }
        composeRule.onNodeWithTag(TAG).performTextInput(input)
        assertEquals(expected, isValid)
    }

    companion object {
        private const val TAG = "text_field"
    }
}