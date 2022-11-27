package com.jeanbarrossilva.ongoing.platform.registry

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.registry.observation.Change
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.core.IsNot.not
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
internal class RoomActivityObserverTests {
    private val activityObserver
        get() = activityRegistry.observer
    private val activityRecorder
        get() = activityRegistry.recorder
    private val activityRegistry
        get() = rule.activityRegistry

    @get:Rule
    val rule = PlatformRegistryTestRule.create()

    @Test(expected = AssertionError::class)
    fun throwWhenAttachingAnObserverToANonexistentActivity() {
        runTest {
            activityObserver.attach(getCurrentUserId(), activityId = "0") { _, _, _ ->
            }
        }
    }

    @Test
    fun attach() {
        runTest {
            val id = activityRegistry.register(getCurrentUserId(), "🙃")
            activityObserver.attach(getCurrentUserId(), id) { _, _, _ -> }
            assertThat(
                activityRegistry.getActivityById(id).first()?.observerUserIds,
                hasItem(getCurrentUserId())
            )
        }
    }

    @Test
    fun notifyNameChange() {
        runTest {
            val oldName = "👀"
            val newName = "🙂"
            val id = activityRegistry.register(getCurrentUserId(), oldName)
            var change: Change? = null
            activityObserver.attach(getCurrentUserId(), id) { _, _, _change ->
                change = _change
            }
            activityRecorder.name(id, newName)
            assertEquals(Change.Name(oldName, newName), change)
        }
    }

    @Test
    fun notifyStatusChange() {
        runTest {
            val activityId = activityRegistry.register(getCurrentUserId(), "🎉")
            val newActivityStatus = Status.DONE
            var change: Change? = null
            activityObserver.attach(getCurrentUserId(), activityId) { _, _, _change ->
                change = _change
            }
            activityRecorder.status(activityId, newActivityStatus)
            assertEquals(Change.Status(Status.TO_DO, newActivityStatus), change)
        }
    }

    @Test
    fun detach() {
        runTest {
            val id = activityRegistry.register(getCurrentUserId(), "🐥")
            activityObserver.attach(getCurrentUserId(), id) { _, _, _ -> }
            activityObserver.detach(getCurrentUserId(), id)
            assertThat(
                activityRegistry.getActivityById(id).first()?.observerUserIds,
                not(hasItem(getCurrentUserId()))
            )
        }
    }

    @Test(expected = AssertionError::class)
    fun throwWhenDetachingAnObserverFromANonexistentActivity() {
        runTest {
            activityObserver.detach(getCurrentUserId(), activityId = "0")
        }
    }

    private suspend fun getCurrentUserId(): String {
        return rule.session.getUser().filterNotNull().first().id
    }
}