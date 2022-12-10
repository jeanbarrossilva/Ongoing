package com.jeanbarrossilva.ongoing

import android.content.Context
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.jeanbarrossilva.ongoing.app.boundary.DefaultActivityDetailsBoundary
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.bridge.ActivityDetailsBridge
import com.jeanbarrossilva.ongoing.feature.activitydetails.bridge.ActivityDetailsBridgeCrossing
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.activityheadline.ActivityHeadlineName
import com.jeanbarrossilva.ongoing.feature.activityediting.component.form.ActivityNameTextField
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.jeanbarrossilva.ongoing.feature.activitydetails.component.scaffold.FloatingActionButton as ActivityDetailsFloatingActionButton
import com.jeanbarrossilva.ongoing.feature.activityediting.component.scaffold.FloatingActionButton as ActivityEditingFloatingActionButton

internal class ActivityUpdateTests {
    private var activityId: String? = null

    private val activityRegistry
        get() = platformRegistryRule.activityRegistry
    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()
    private val session
        get() = platformRegistryRule.session

    @get:Rule
    val platformRegistryRule = PlatformRegistryTestRule.create()

    @get:Rule
    val composeRule = createEmptyComposeRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        runTest {
            activityId = activityRegistry.register(getCurrentUserId(), name = "Have breakfast")
        }
        crossBridge()
    }

    @Test
    fun givenANameEditWhenGoingBackToDetailsThenItsUpToDate() {
        val editedName = "Have lunch"
        composeRule.onNodeWithTag(ActivityDetailsFloatingActionButton.TAG).performClick()
        composeRule.onNodeWithTag(ActivityNameTextField.TAG).performTextReplacement(editedName)
        composeRule.onNodeWithTag(ActivityEditingFloatingActionButton.TAG).performClick()
        composeRule.onNodeWithTag(ActivityHeadlineName.TAG).assertTextEquals(editedName)
    }

    private suspend fun getCurrentUserId(): String {
        return session.getUser().filterNotNull().first().id
    }

    private fun crossBridge() {
        val userRepository = InMemoryUserRepository(session)
        val fetcher = ContextualActivitiesFetcher(session, userRepository, activityRegistry)
        val boundary = DefaultActivityDetailsBoundary()
        val intent = ActivityDetailsActivity.getIntent(context, requireNotNull(activityId))
        ActivityDetailsBridge.cross(
            context,
            session,
            activityRegistry,
            Observation.empty,
            fetcher,
            boundary,
            ActivityDetailsBridgeCrossing.RetentionOnly
        )
        ActivityScenario.launch<ActivityDetailsActivity>(intent)
    }
}