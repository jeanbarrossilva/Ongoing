package com.jeanbarrossilva.ongoing.platform.registry

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.platform.registry.activity.RoomActivityRegistry
import com.jeanbarrossilva.ongoing.platform.registry.authorization.CurrentUserIdProvider
import com.jeanbarrossilva.ongoing.platform.registry.extensions.uuid
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
internal class RoomActivityRegistryTests {
    private lateinit var database: OngoingDatabase
    private val currentUserId = uuid()
    private val currentUserIdProvider = CurrentUserIdProvider { currentUserId }

    private val context
        get() = ApplicationProvider.getApplicationContext<Context>()
    private val activityRegistry
        get() = RoomActivityRegistry(database.activityDao, currentUserIdProvider)

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(context, OngoingDatabase::class.java).build()
    }

    @After
    fun tearDown() {
        runTest { activityRegistry.clear() }
        database.close()
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
                assertEquals(Status.all, activity.statuses)
                assertEquals(Status.TO_DO, activity.currentStatus)
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