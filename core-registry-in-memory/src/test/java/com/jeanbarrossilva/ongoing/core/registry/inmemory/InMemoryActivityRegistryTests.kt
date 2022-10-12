package com.jeanbarrossilva.ongoing.core.registry.inmemory

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.registry.inmemory.extensions.uuid
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.UUID
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
internal class InMemoryActivityRegistryTests {
    private val activityRegistry = InMemoryActivityRegistry()

    @BeforeTest
    fun setUp() {
        runTest {
            activityRegistry.clear()
        }
    }

    @Test
    fun `GIVEN an activity WHEN registering it THEN it's correctly registered`() {
        val ownerUserId = uuid()
        val name = "Shop"
        runTest {
            activityRegistry.register(ownerUserId, name)
            activityRegistry.getActivities().map(List<Activity>::first).first().let { activity ->
                assertDoesNotThrow { UUID.fromString(activity.id) }
                assertEquals(ownerUserId, activity.ownerUserId)
                assertEquals(name, activity.name)
                assertEquals(Icon.OTHER, activity.icon)
                assertEquals(Status.all, activity.statuses)
                assertEquals(Status.TO_DO, activity.currentStatus)
            }
        }
    }

    @Test
    fun `GIVEN a registered activity WHEN getting it by its ID THEN it isn't null`() {
        runTest {
            val id = activityRegistry.register(ownerUserId = uuid(), name = "Walk the dog")
            assertNotNull(activityRegistry.getActivityById(id))
        }
    }

    @Test
    fun `GIVEN an activity WHEN changing its name THEN it's changed`() {
        runTest {
            val id = activityRegistry.register(ownerUserId = uuid(), name = "Move to a new house")
            val name = "Move to a new apartment"
            activityRegistry.setName(id, name)
            assertEquals(name, activityRegistry.getActivityById(id)?.name)
        }
    }

    @Test
    fun `GIVEN an activity WHEN changing its icon THEN it's changed`() {
        runTest {
            val id = activityRegistry.register(ownerUserId = uuid(), name = "Study")
            val icon = Icon.BOOK
            activityRegistry.setIcon(id, icon)
            assertEquals(icon, activityRegistry.getActivityById(id)?.icon)
        }
    }

    @Test
    fun `GIVEN an activity WHEN changing its current status THEN it's changed`() {
        runTest {
            val id = activityRegistry.register(ownerUserId = uuid(), name = "Listen to music")
            val status = Status.ONGOING
            activityRegistry.setCurrentStatus(id, status)
            assertEquals(status, activityRegistry.getActivityById(id)?.currentStatus)
        }
    }

    @Test
    fun `GIVEN an activity WHEN unregistering it THEN it's unregistered`() {
        runTest {
            val id = activityRegistry.register(ownerUserId = uuid(), name = "Clean the room")
            activityRegistry.unregister(id)
            assertNull(activityRegistry.getActivityById(id))
        }
    }

    @Test
    fun `GIVEN an activity WHEN unregistering it twice THEN it throws`() {
        runTest {
            val id = activityRegistry.register(ownerUserId = uuid(), name = "Run a marathon")
            activityRegistry.unregister(id)
            assertFailsWith<IllegalArgumentException> { activityRegistry.unregister(id) }
        }
    }

    @Test
    fun `GIVEN various activities WHEN clearing them THEN they're gone`() {
        runTest {
            activityRegistry.register(ownerUserId = uuid(), name = "Do homework")
            activityRegistry.register(ownerUserId = uuid(), name = "Fly to SF")
            activityRegistry.clear()
            assertEquals(emptyList(), activityRegistry.getActivities().first())
        }
    }
}