package com.jeanbarrossilva.ongoing.platform.registry

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.core.registry.observation.Change
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySession
import com.jeanbarrossilva.ongoing.platform.registry.extensions.getActivityRegistry
import com.jeanbarrossilva.ongoing.platform.registry.extensions.uuid
import com.jeanbarrossilva.ongoing.platform.registry.test.OngoingDatabaseRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
internal class RoomActivityObserverTests {
    private val session = InMemorySession()
    private lateinit var activityRegistry: ActivityRegistry

    private val activityObserver
        get() = activityRegistry.observer
    private val activityRecorder
        get() = activityRegistry.recorder

    @get:Rule
    val databaseRule = OngoingDatabaseRule()

    @Before
    fun setUp() {
        runTest {
            session.logIn()
            activityRegistry = databaseRule.getDatabase().getActivityRegistry(session)
        }
    }

    @After
    fun tearDown() {
        runTest {
            activityRegistry.clear()
            session.logOut()
        }
    }

    @Test(expected = AssertionError::class)
    fun throwWhenAttachingAnObserverToANonexistentActivity() {
        runTest {
            withCurrentUserId {
                activityObserver.attach(this, activityId = uuid()) {
                }
            }
        }
    }

    @Test
    fun attach() {
        runTest {
            withCurrentUserId {
                val activityId = activityRegistry.register("🙃")
                activityObserver.attach(this, activityId) { }
                assertThat(
                    activityRegistry.getActivityById(activityId).first()?.observerUserIds,
                    hasItem(this)
                )
            }
        }
    }

    @Test
    fun notifyNameChange() {
        runTest {
            withCurrentUserId {
                val activityId = activityRegistry.register("👀")
                var change: Change? = null
                activityObserver.attach(this, activityId) { change = it }
                activityRecorder.name(activityId, "🙂")
                assertEquals(Change.NAME, change)
            }
        }
    }

    @Test
    fun notifyStatusChange() {
        runTest {
            withCurrentUserId {
                val activityId = activityRegistry.register("🎉")
                var change: Change? = null
                activityObserver.attach(this, activityId) { change = it }
                activityRecorder.status(activityId, Status.DONE)
                assertEquals(Change.STATUS, change)
            }
        }
    }

    @Test
    fun detach() {
        runTest {
            withCurrentUserId {
                val activityId = activityRegistry.register("🐥")
                activityObserver.attach(this, activityId) { }
                activityObserver.detach(this, activityId)
                assertThat(
                    activityRegistry.getActivityById(activityId).first()?.observerUserIds,
                    not(hasItem(this))
                )
            }
        }
    }

    @Test(expected = AssertionError::class)
    fun throwWhenDetachingAnObserverFromANonexistentActivity() {
        runTest {
            withCurrentUserId {
                activityObserver.detach(this, activityId = "🫠")
            }
        }
    }

    private suspend inline fun withCurrentUserId(operation: String.() -> Unit) {
        session.getUser().first()?.id?.operation()
    }
}