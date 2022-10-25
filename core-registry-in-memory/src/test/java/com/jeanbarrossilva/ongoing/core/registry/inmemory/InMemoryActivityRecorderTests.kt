package com.jeanbarrossilva.ongoing.core.registry.inmemory

import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.registry.inmemory.extensions.uuid
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class InMemoryActivityRecorderTests {
    private val activityRegistry = InMemoryActivityRegistry()
    private val activityRecorder = activityRegistry.recorder

    @Test
    fun `GIVEN an activity WHEN changing its name THEN it's changed`() {
        runTest {
            val id = activityRegistry.register(ownerUserId = uuid(), name = "Move to a new house")
            val name = "Move to a new apartment"
            activityRecorder.name(id, name)
            assertEquals(name, activityRegistry.getActivityById(id)?.name)
        }
    }

    @Test
    fun `GIVEN an activity WHEN changing its icon THEN it's changed`() {
        runTest {
            val id = activityRegistry.register(ownerUserId = uuid(), name = "Study")
            val icon = Icon.BOOK
            activityRecorder.icon(id, icon)
            assertEquals(icon, activityRegistry.getActivityById(id)?.icon)
        }
    }

    @Test
    fun `GIVEN an activity WHEN changing its current status THEN it's changed`() {
        runTest {
            val id = activityRegistry.register(ownerUserId = uuid(), name = "Listen to music")
            val status = Status.ONGOING
            activityRecorder.currentStatus(id, status)
            assertEquals(status, activityRegistry.getActivityById(id)?.currentStatus)
        }
    }

    @Test
    fun `GIVEN a registered status listener WHEN changing the status of an activity THEN it's notified`() {
        runTest {
            val id = activityRegistry.register(ownerUserId = uuid(), name = "Swim")
            val status = Status.DONE
            var hasBeenNotified = false
            activityRecorder.doOnStatusChange {
                hasBeenNotified = true
                assertEquals(status, it.currentStatus)
            }
            activityRecorder.currentStatus(id, status)
            assertTrue(hasBeenNotified)
        }
    }
}