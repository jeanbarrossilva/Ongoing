package com.jeanbarrossilva.ongoing.platform.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.platform.registry.authorization.CurrentUserIdProvider
import com.jeanbarrossilva.ongoing.platform.registry.extensions.getActivityRegistry
import com.jeanbarrossilva.ongoing.platform.registry.extensions.uuid
import com.jeanbarrossilva.ongoing.platform.registry.rule.OngoingDatabaseRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class RoomActivityRegistryTests {
    private val currentUserId = uuid()
    private val currentUserIdProvider = CurrentUserIdProvider { currentUserId }

    private val activityRegistry
        get() = databaseRule.database.getActivityRegistry(currentUserIdProvider)

    @get:Rule
    val databaseRule = OngoingDatabaseRule()

    @After
    fun tearDown() {
        runTest {
            activityRegistry.clear()
        }
    }

    @Test
    fun getActivities() {
        runTest {
            0.until(11).forEach { activityRegistry.register("Activity #$it") }
            assertEquals(
                0.until(11).map { "Activity #$it" },
                activityRegistry.getActivities().first().map(Activity::name)
            )
        }
    }

    @Test
    fun registerActivity() {
        val name = "Shop"
        runTest {
            activityRegistry.register(name)
            activityRegistry.getActivities().map(List<Activity>::first).first().let { activity ->
                assertEquals(currentUserId, activity.ownerUserId)
                assertEquals(name, activity.name)
                assertEquals(Icon.OTHER, activity.icon)
                assertEquals(listOf(Status.TO_DO), activity.statuses)
            }
        }
    }

    @Test(expected = AssertionError::class)
    fun throwWhenRegisteringActivityWithABlankName() {
        runTest {
            activityRegistry.register(" ")
        }
    }

    @Test
    fun getActivityById() {
        runTest {
            val id = activityRegistry.register("Walk the dog")
            assertNotNull(activityRegistry.getActivityById(id).first())
        }
    }

    @Test
    fun unregister() {
        runTest {
            val id = activityRegistry.register("Clean the room")
            activityRegistry.unregister(id)
            assertNull(activityRegistry.getActivityById(id).first())
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun throwWhenUnregisteringTheSameActivityTwice() {
        runTest {
            val id = activityRegistry.register("Run a marathon")
            activityRegistry.unregister(id)
            activityRegistry.unregister(id)
        }
    }

    @Test
    fun clear() {
        runTest {
            activityRegistry.register("Do homework")
            activityRegistry.register("Fly to SF")
            activityRegistry.clear()
            assertEquals(emptyList<Activity>(), activityRegistry.getActivities().first())
        }
    }
}