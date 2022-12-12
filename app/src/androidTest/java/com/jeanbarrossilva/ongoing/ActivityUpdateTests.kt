package com.jeanbarrossilva.ongoing

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.core.content.getSystemService
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.ongoing.app.BuildConfig
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus
import com.jeanbarrossilva.ongoing.extensions.hasNodeThat
import com.jeanbarrossilva.ongoing.extensions.hasTestTagPrefixedWith
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesActivity
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.ActivityCard
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.ActivityNameTextField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownField
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.status.ActivityStatusDropdownMenuItem
import com.jeanbarrossilva.ongoing.feature.authentication.Authentication
import com.jeanbarrossilva.ongoing.feature.authentication.component.buttons.DismissButton
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.component.NavigationButton
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.component.headline.component.ActivityHeadlineName as ActivityCardHeadlineName
import com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.FloatingActionButton as ActivitiesFloatingActionButton
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.activityheadline.ActivityHeadlineName as ActivityDetailsHeadlineName
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold.FloatingActionButton as ActivityDetailsFloatingActionButton
import com.jeanbarrossilva.ongoing.feature.activityediting.component.scaffold.FloatingActionButton as ActivityEditingFloatingActionButton

internal class ActivityUpdateTests {
    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    val composeRule = createAndroidComposeRule<ActivitiesActivity>()

    @Before
    fun setUp() {
        warnAboutAppFlavorIfItIsNotClean()
        dismissAuthenticationPromptIfDisplayed()
    }

    @After
    fun tearDown() {
        context?.getSystemService<ActivityManager>()?.clearApplicationUserData()
    }

    @Test
    fun synchronizeNameEdit() {
        val oldName = "Have breakfast"
        val newName = "Have lunch"
        composeRule.onNodeWithTag(ActivitiesFloatingActionButton.TAG).performClick()
        editActivity(oldName)
        composeRule.onNode(hasTestTagPrefixedWith(ActivityCard.TAG)).performClick()
        waitForActivityDetailsToLoad()
        composeRule.onNodeWithTag(ActivityDetailsFloatingActionButton.TAG).performClick()
        editActivity(newName, status = null)
        waitForActivityDetailsToLoad()
        composeRule.onNodeWithTag(ActivityDetailsHeadlineName.TAG).assertTextEquals(newName)
        composeRule.onNodeWithTag(NavigationButton.TAG).performClick()
        composeRule.onNodeWithTag(ActivityCardHeadlineName.TAG).assertTextEquals(newName)
    }

    @Suppress("KotlinConstantConditions")
    private fun warnAboutAppFlavorIfItIsNotClean() {
        val cleanFlavor = "clean"
        val isNotCleanFlavor = BuildConfig.FLAVOR != cleanFlavor
        if (isNotCleanFlavor) {
            Log.w(TAG, "These tests are suited for the '$cleanFlavor' app flavor.")
        }
    }

    private fun dismissAuthenticationPromptIfDisplayed() {
        val isPrompted = composeRule.hasNodeThat(hasTestTag(Authentication.TAG))
        if (isPrompted) {
            composeRule.onNodeWithTag(DismissButton.TAG).performClick()
        }
    }

    private fun editActivity(name: String, status: ContextualStatus? = ContextualStatus.TO_DO) {
        composeRule.onNodeWithTag(ActivityNameTextField.TAG).performTextReplacement(name)
        composeRule.onNodeWithTag(ActivityStatusDropdownField.TAG).performClick()
        status?.let {
            composeRule.onNodeWithTag(ActivityStatusDropdownMenuItem.getTag(it)).performClick()
        }
        composeRule.onNodeWithTag(ActivityEditingFloatingActionButton.TAG).performClick()
    }

    private fun waitForActivityDetailsToLoad() {
        with(composeRule) {
            waitUntil {
                hasNodeThat(hasTestTag(ActivityDetailsFloatingActionButton.TAG))
            }
        }
    }

    companion object {
        private const val TAG = "ActivityUpdateTests"
    }
}