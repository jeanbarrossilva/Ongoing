package com.jeanbarrossilva.ongoing.core.registry

import com.jeanbarrossilva.ongoing.core.registry.inmemory.InMemoryActivityRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
internal class ActivityRegistryTests {
    private val activityRegistry: ActivityRegistry = InMemoryActivityRegistry()
    private val userId = uuid()

    @AfterTest
    fun tearDown() {
        runTest {
            activityRegistry.clear(userId)
        }
    }

    @Test
    fun `GIVEN a name and an owner user ID WHEN registering an activity with it THEN it is correctly registered`() {
        runTest {
            val name = "Learn Flutter"
            val activityId = activityRegistry.register(userId, name)
            val activity = activityRegistry.getActivityById(activityId).first()
            val expectedActivity = ActivityRegistry.createActivity(userId, activityId, name)
            assertNotNull(activity)
            assertEquals(expectedActivity.id, activity.id)
            assertEquals(expectedActivity.ownerUserId, activity.ownerUserId)
            assertEquals(expectedActivity.name, activity.name)
            assertEquals(expectedActivity.icon, activity.icon)
            assertEquals(expectedActivity.statuses, activity.statuses)
            assertEquals(expectedActivity.observerUserIds, activity.observerUserIds)
        }
    }

    @Test
    fun `GIVEN an activity ID WHEN unregistering with it as the owner THEN the activity is unregistered`() {
        runTest {
            val ownerUserId = uuid()
            val activityId = activityRegistry.register(ownerUserId, name = "Stop procrastinating")
            activityRegistry.unregister(ownerUserId, activityId)
            assertNull(activityRegistry.getActivityById(activityId).first())
        }
    }

    @Test
    fun `GIVEN an activity ID WHEN unregistering with it as a non-owner THEN it throws`() {
        runTest {
            val activityId =
                activityRegistry.register(userId, name = "Unregister this activity")
            assertFailsWith<IllegalArgumentException> {
                activityRegistry.unregister(userId = uuid(), activityId)
            }
        }
    }

    @Test
    fun `GIVEN various registered activities WHEN clearing THEN they've been unregistered`() {
        runTest {
            repeat(128) {
                activityRegistry.register(userId, name = "Do whatever whenever with whomever")
            }
            activityRegistry.clear(userId)
            assertEquals(emptyList(), activityRegistry.activities.first())
        }
    }
}