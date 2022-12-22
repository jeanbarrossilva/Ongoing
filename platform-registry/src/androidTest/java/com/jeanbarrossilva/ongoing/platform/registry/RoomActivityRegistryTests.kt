package com.jeanbarrossilva.ongoing.platform.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    private val currentUserId
        get() = requireNotNull(rule.sessionManager.session<Session.SignedIn>()).userId

    @get:Rule
    val rule = PlatformRegistryTestRule.create()

    @Test
    fun getActivities() {
        runTest {
            0.until(11).forEach { activityRegistry.register(currentUserId, "Activity #$it") }
            assertThat(
                activityRegistry.getActivities().map(Activity::name),
                containsInAnyOrder(*0.until(11).map { "Activity #$it" }.toTypedArray())
            )
        }
    }

    @Test
    fun registerActivity() {
        val name = "Shop"
        runTest {
            val id = activityRegistry.register(currentUserId, name)
            assertNotNull(activityRegistry.getActivityById(id))
        }
    }

    @Test
    fun getActivityById() {
        runTest {
            val activityId = activityRegistry.register(currentUserId, "Walk the dog")
            assertNotNull(activityRegistry.getActivityById(activityId))
        }
    }

    @Test
    fun unregister() {
        runTest {
            val activityId = activityRegistry.register(currentUserId, "Clean the room")
            activityRegistry.unregister(currentUserId, activityId)
            assertNull(activityRegistry.getActivityById(activityId))
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun throwWhenUnregisteringTheSameActivityTwice() {
        runTest {
            val activityId = activityRegistry.register(currentUserId, "Run a marathon")
            activityRegistry.unregister(currentUserId, activityId)
            activityRegistry.unregister(currentUserId, activityId)
        }
    }

    @Test
    fun clear() {
        runTest {
            activityRegistry.register(currentUserId, "Do homework")
            activityRegistry.register(currentUserId, "Fly to SF")
            activityRegistry.clear(currentUserId)
            assertEquals(emptyList<Activity>(), activityRegistry.getActivities())
        }
    }
}