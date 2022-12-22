package com.jeanbarrossilva.ongoing.platform.registry

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.registry.observation.Change
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.platform.registry.test.PlatformRegistryTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    private val currentUserId
        get() = requireNotNull(rule.sessionManager.session<Session.SignedIn>()).userId

    @get:Rule
    val rule = PlatformRegistryTestRule.create()

    @Test(expected = AssertionError::class)
    fun throwWhenAttachingAnObserverToANonexistentActivity() {
        runTest {
            activityObserver.attach(currentUserId, activityId = "0") { _, _, _ ->
            }
        }
    }

    @Test
    fun attach() {
        runTest {
            val id = activityRegistry.register(currentUserId, "🙃")
            activityObserver.attach(currentUserId, id) { _, _, _ -> }
            assertThat(
                activityRegistry.getActivityById(id)?.observerUserIds,
                hasItem(currentUserId)
            )
        }
    }

    @Test
    fun notifyNameChange() {
        runTest {
            val oldName = "👀"
            val newName = "🙂"
            val id = activityRegistry.register(currentUserId, oldName)
            var change: Change? = null
            activityObserver.attach(currentUserId, id) { _, _, _change ->
                change = _change
            }
            activityRecorder.name(id, newName)
            assertEquals(Change.Name(oldName, newName), change)
        }
    }

    @Test
    fun notifyStatusChange() {
        runTest {
            val id = activityRegistry.register(currentUserId, "🎉")
            val newStatus = Status.DONE
            var change: Change? = null
            activityObserver.attach(currentUserId, id) { _, _, _change -> change = _change }
            activityRecorder.status(id, newStatus)
            assertEquals(Change.Status(Status.TO_DO, newStatus), change)
        }
    }

    @Test
    fun detach() {
        runTest {
            val id = activityRegistry.register(currentUserId, "🐥")
            activityObserver.attach(currentUserId, id) { _, _, _ -> }
            activityObserver.detach(currentUserId, id)
            assertThat(
                activityRegistry.getActivityById(id)?.observerUserIds,
                not(hasItem(currentUserId))
            )
        }
    }

    @Test(expected = AssertionError::class)
    fun throwWhenDetachingAnObserverFromANonexistentActivity() {
        runTest {
            activityObserver.detach(currentUserId, activityId = "0")
        }
    }
}