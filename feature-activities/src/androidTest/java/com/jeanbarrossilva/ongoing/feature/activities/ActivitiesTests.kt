package com.jeanbarrossilva.ongoing.feature.activities

import android.content.Context
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.extensions.getActivities
import com.jeanbarrossilva.ongoing.context.registry.extensions.register
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.feature.activities.component.REMOVAL_CONFIRMATION_DIALOG_CONFIRMATION_BUTTON_TAG
import com.jeanbarrossilva.ongoing.feature.activities.component.activitycard.ActivityCard
import com.jeanbarrossilva.ongoing.feature.activities.component.scaffold.topappbar.TOP_APP_BAR_SELECTION_ACTIONS_REMOVE_TAG
import com.jeanbarrossilva.ongoing.feature.activities.extensions.hasTestTagPrefixedWith
import com.jeanbarrossilva.ongoing.platform.designsystem.component.scaffold.topappbar.TOP_APP_BAR_TAG
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.unwrap
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class ActivitiesTests {
    private lateinit var fetcher: ContextualActivitiesFetcher

    private val activityCard
        get() = composeRule.onNode(hasTestTagPrefixedWith(ActivityCard.TAG))
    private val activityRegistry
        get() = platformRegistryRule.activityRegistry
    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()
    private val session
        get() = platformRegistryRule.session

    @get:Rule
    val platformRegistryRule = PlatformRegistryTestRule.create()

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        init()
        setContent()
    }

    @Test
    fun enterSelectionModeOnActivityLongClick() {
        registerActivity()
        longClickFirstActivity()
        assertTopAppBarTitleEquals(
            context.resources.getQuantityString(R.plurals.feature_activities_selection, 1, 1)
        )
        assertTrue(activityCard.fetchSemanticsNode().config[SemanticsProperties.Selected])
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun removeActivity() {
        registerActivity()
        removeFirstActivity()
        runTest {
            fetcher.getActivities().unwrap().test {
                assertEquals(emptyList<ContextualActivity>(), awaitItem())
            }
        }
    }

    @Test
    fun exitSelectionModeAfterRemovingAnActivity() {
        registerActivity()
        removeFirstActivity()
        assertTopAppBarTitleEquals(context.getString(R.string.feature_activities))
    }

    private fun init() {
        val userRepository = InMemoryUserRepository(session)
        fetcher = ContextualActivitiesFetcher(session, userRepository, activityRegistry)
    }

    private fun setContent() {
        val viewModel = ActivitiesViewModel(session, fetcher)
        composeRule.setContent {
            Activities(viewModel, ActivitiesBoundary.empty, activityRegistry, Observation.empty)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun registerActivity() {
        runTest {
            fetcher.register(ContextualActivity.sample)
        }
    }

    private fun removeFirstActivity() {
        longClickFirstActivity()
        composeRule.onNodeWithTag(TOP_APP_BAR_SELECTION_ACTIONS_REMOVE_TAG).performClick()
        composeRule.onNodeWithTag(REMOVAL_CONFIRMATION_DIALOG_CONFIRMATION_BUTTON_TAG)
            .performClick()
    }

    private fun longClickFirstActivity() {
        activityCard.performTouchInput {
            longClick(center)
        }
    }

    private fun assertTopAppBarTitleEquals(expected: String) {
        composeRule
            .onNodeWithTag(TOP_APP_BAR_TAG)
            .onChildren()
            .filterToOne(hasTextExactly(expected))
            .assertExists()
    }
}