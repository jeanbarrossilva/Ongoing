package com.jeanbarrossilva.ongoing.app

import android.app.ActivityManager
import android.content.Context
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.core.content.getSystemService
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus
import com.jeanbarrossilva.ongoing.app.extensions.createAndroidComposeRule
import com.jeanbarrossilva.ongoing.app.extensions.hasNodeThat
import com.jeanbarrossilva.ongoing.app.extensions.hasTestTagPrefixedWith
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.ActivityCard
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.component.StatusIndicator
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.ActivityStatusHistory
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
    val composeRule = createAndroidComposeRule()

    @Before
    fun setUp() {
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

    @Test
    fun synchronizeStatusEdit() {
        val oldStatus = ContextualStatus.TO_DO
        val newStatus = ContextualStatus.DONE
        val newStatusTitle = context.getString(newStatus.titleRes)
        composeRule.onNodeWithTag(ActivitiesFloatingActionButton.TAG).performClick()
        editActivity(name = "Take out the trash", oldStatus)
        composeRule.onNodeWithTag(ActivityDetailsFloatingActionButton.TAG).performClick()
        editActivity(name = null, newStatus)
        waitForActivityDetailsToLoad()
        composeRule
            .onNodeWithTag(ActivityStatusHistory.TAG)
            .onChildren()
            .filterToOne(hasTextExactly(newStatusTitle))
            .assertExists()
        composeRule.onNodeWithTag(NavigationButton.TAG).performClick()
        composeRule.onNodeWithTag(StatusIndicator.TAG).assertTextEquals(newStatusTitle)
    }

    private fun dismissAuthenticationPromptIfDisplayed() {
        val isPrompted = composeRule.hasNodeThat(hasTestTag(Authentication.TAG))
        if (isPrompted) {
            composeRule.onNodeWithTag(DismissButton.TAG).performClick()
        }
    }

    private fun editActivity(name: String?, status: ContextualStatus? = ContextualStatus.TO_DO) {
        name?.let {
            composeRule.onNodeWithTag(ActivityNameTextField.TAG).performTextReplacement(it)
        }
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
}