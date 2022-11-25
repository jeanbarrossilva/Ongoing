package com.jeanbarrossilva.ongoing.platform.registry

import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.platform.registry.test.OngoingDatabaseRule
import com.jeanbarrossilva.ongoing.platform.registry.test.extensions.activityRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class RoomActivityRecorderTests {
    private val activityRegistry
        get() = databaseRule.getDatabase().activityRegistry
    private val activityRecorder
        get() = activityRegistry.recorder

    @get:Rule
    val databaseRule = OngoingDatabaseRule()

    @Test
    fun updateName() {
        val name = ":)"
        runTest {
            val id = activityRegistry.register("Do something")
            activityRecorder.name(id, name)
            assertEquals(name, activityRegistry.getActivityById(id).first()?.name)
        }
    }

    @Test
    fun updateIcon() {
        val icon = Icon.BOOK
        runTest {
            val id = activityRegistry.register("Travel to Japan")
            activityRecorder.icon(id, icon)
            assertEquals(icon, activityRegistry.getActivityById(id).first()?.icon)
        }
    }

    @Test
    fun updateStatus() {
        val status = Status.DONE
        runTest {
            val id = activityRegistry.register("Play")
            activityRecorder.status(id, status)
            assertEquals(status, activityRegistry.getActivityById(id).first()?.status)
        }
    }

    @Test
    fun doesntUpdateStatusWhenSettingItTwice() {
        val status = Status.DONE
        runTest {
            val id = activityRegistry.register("Test the app")
            activityRecorder.status(id, status)
            activityRecorder.status(id, status)
            assertEquals(
                listOf(Status.TO_DO, Status.DONE),
                activityRegistry.getActivityById(id).first()?.statuses
            )
        }
    }
}