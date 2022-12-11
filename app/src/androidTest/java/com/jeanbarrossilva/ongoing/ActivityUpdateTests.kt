package com.jeanbarrossilva.ongoing

import android.util.Log
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus
import com.jeanbarrossilva.ongoing.extensions.hasNodeWithTag
import com.jeanbarrossilva.ongoing.extensions.hasTestTagPrefixedWith
import com.jeanbarrossilva.ongoing.extensions.pressBack
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.ActivityCard
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.activityheadline.ActivityHeadlineName
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.ActivityNameTextField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownMenuItem
import com.jeanbarrossilva.ongoing.feature.authentication.Authentication
import com.jeanbarrossilva.ongoing.feature.authentication.component.buttons.DismissButton
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.component.headline.component.ActivityHeadlineName as ActivityCardHeadlineName
import com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.FloatingActionButton as ActivitiesFloatingActionButton
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold.FloatingActionButton as ActivityDetailsFloatingActionButton
import com.jeanbarrossilva.ongoing.feature.activityediting.component.scaffold.FloatingActionButton as ActivityEditingFloatingActionButton

internal class ActivityUpdateTests {
    @get:Rule
    val composeRule = createAndroidComposeRule<OngoingActivity>()

    @Before
    fun setUp() {
        warnAboutAppFlavorIfItIsNotClean()
        dismissAuthenticationPromptIfDisplayed()
    }

    @Test
    fun synchronizeNameEdit() {
        val oldName = "Have breakfast"
        val newName = "Have lunch"
        composeRule.onNodeWithTag(ActivitiesFloatingActionButton.TAG).performClick()
        editActivity(oldName)
        composeRule.onNode(hasTestTagPrefixedWith(ActivityCard.TAG)).performClick()
        composeRule.onNodeWithTag(ActivityDetailsFloatingActionButton.TAG).performClick()
        composeRule.onNodeWithTag(ActivityHeadlineName.TAG).assertTextEquals(newName)
        composeRule.pressBack()
        composeRule.onNodeWithTag(ActivityCardHeadlineName.TAG).assertTextEquals(newName)
    }

    @Suppress("KotlinConstantConditions")
    private fun warnAboutAppFlavorIfItIsNotClean() {
        val flavor = "clean"
        val isNotCleanFlavor = BuildConfig.FLAVOR != flavor
        if (isNotCleanFlavor) {
            Log.w(TAG, "These tests are suited for the '$flavor' app flavor.")
        }
    }

    private fun dismissAuthenticationPromptIfDisplayed() {
        val isPrompted = composeRule.hasNodeWithTag(Authentication.TAG)
        if (isPrompted) {
            composeRule.onNodeWithTag(DismissButton.TAG).performClick()
        }
    }

    private fun editActivity(name: String, status: ContextualStatus = ContextualStatus.TO_DO) {
        composeRule.onNodeWithTag(ActivityNameTextField.TAG).performTextInput(name)
        composeRule.onNodeWithTag(ActivityStatusDropdownField.TAG).performClick()
        composeRule.onNodeWithTag(ActivityStatusDropdownMenuItem.getTag(status)).performClick()
        composeRule.onNodeWithTag(ActivityEditingFloatingActionButton.TAG).performClick()
    }

    companion object {
        private const val TAG = "ActivityUpdateTests"
    }
}