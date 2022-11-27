package com.jeanbarrossilva.ongoing.platform.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class RoomActivityRegistryTests {
    private val activityRegistry
        get() = rule.activityRegistry

    @get:Rule
    val rule = PlatformRegistryTestRule.create()

    @Test
    fun getActivities() {
        runTest {
            0.until(11).forEach { activityRegistry.register(getCurrentUserId(), "Activity #$it") }
            assertThat(
                activityRegistry.activities.first().map(Activity::name),
                containsInAnyOrder(*0.until(11).map { "Activity #$it" }.toTypedArray())
            )
        }
    }

    @Test
    fun registerActivity() {
        val name = "Shop"
        runTest {
            val id = activityRegistry.register(getCurrentUserId(), name)
            assertNotNull(activityRegistry.getActivityById(id))
        }
    }

    @Test(expected = AssertionError::class)
    fun throwWhenRegisteringActivityWithABlankName() {
        runTest {
            activityRegistry.register(getCurrentUserId(), " ")
        }
    }

    @Test
    fun getActivityById() {
        runTest {
            val activityId = activityRegistry.register(getCurrentUserId(), "Walk the dog")
            assertNotNull(activityRegistry.getActivityById(activityId).first())
        }
    }

    @Test
    fun unregister() {
        runTest {
            val activityId = activityRegistry.register(getCurrentUserId(), "Clean the room")
            activityRegistry.unregister(getCurrentUserId(), activityId)
            assertNull(activityRegistry.getActivityById(activityId).first())
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun throwWhenUnregisteringTheSameActivityTwice() {
        runTest {
            val activityId = activityRegistry.register(getCurrentUserId(), "Run a marathon")
            activityRegistry.unregister(getCurrentUserId(), activityId)
            activityRegistry.unregister(getCurrentUserId(), activityId)
        }
    }

    @Test
    fun clear() {
        runTest {
            activityRegistry.register(getCurrentUserId(), "Do homework")
            activityRegistry.register(getCurrentUserId(), "Fly to SF")
            activityRegistry.clear(getCurrentUserId())
            assertEquals(emptyList<Activity>(), activityRegistry.activities.first())
        }
    }

    private suspend fun getCurrentUserId(): String {
        return rule.session.getUser().filterNotNull().first().id
    }
}