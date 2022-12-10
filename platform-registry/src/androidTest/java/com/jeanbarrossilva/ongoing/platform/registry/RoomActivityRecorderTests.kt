package com.jeanbarrossilva.ongoing.platform.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class RoomActivityRecorderTests {
    private val activityRecorder
        get() = activityRegistry.recorder
    private val activityRegistry
        get() = rule.activityRegistry

    @get:Rule
    val rule = PlatformRegistryTestRule.create()

    @Test
    fun updateName() {
        val name = ":)"
        runTest {
            val id = activityRegistry.register(getCurrentUserId(), "Do something")
            activityRecorder.name(id, name)
            assertEquals(name, activityRegistry.getActivityById(id)?.name)
        }
    }

    @Test
    fun updateIcon() {
        val icon = Icon.BOOK
        runTest {
            val id = activityRegistry.register(getCurrentUserId(), "Travel to Japan")
            activityRecorder.icon(id, icon)
            assertEquals(icon, activityRegistry.getActivityById(id)?.icon)
        }
    }

    @Test
    fun updateStatus() {
        val status = Status.DONE
        runTest {
            val id = activityRegistry.register(getCurrentUserId(), "Play")
            activityRecorder.status(id, status)
            assertEquals(status, activityRegistry.getActivityById(id)?.status)
        }
    }

    @Test
    fun doesntUpdateStatusWhenSettingItTwice() {
        val status = Status.DONE
        runTest {
            val id = activityRegistry.register(getCurrentUserId(), "Test the app")
            activityRecorder.status(id, status)
            activityRecorder.status(id, status)
            assertEquals(
                listOf(Status.TO_DO, Status.DONE),
                activityRegistry.getActivityById(id)?.statuses
            )
        }
    }

    private suspend fun getCurrentUserId(): String {
        return rule.session.getUser().filterNotNull().first().id
    }
}