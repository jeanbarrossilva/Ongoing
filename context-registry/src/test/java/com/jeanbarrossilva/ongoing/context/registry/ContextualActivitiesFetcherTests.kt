package com.jeanbarrossilva.ongoing.context.registry

import app.cash.turbine.test
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.OnFetchListener
import com.jeanbarrossilva.ongoing.context.registry.extensions.clear
import com.jeanbarrossilva.ongoing.context.registry.extensions.getActivities
import com.jeanbarrossilva.ongoing.context.registry.extensions.getActivity
import com.jeanbarrossilva.ongoing.context.registry.extensions.register
import com.jeanbarrossilva.ongoing.core.registry.inmemory.InMemoryActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySession
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.emptySerializableList
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toSerializableList
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.unwrap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ContextualActivitiesFetcherTests {
    private val session = InMemorySession()
    private val userRepository = InMemoryUserRepository(session)
    private val activityRegistry = InMemoryActivityRegistry()
    private val fetcher = ContextualActivitiesFetcher(session, userRepository, activityRegistry)

    @Before
    fun setUp() {
        runTest {
            session.logIn()
        }
    }

    @After
    fun tearDown() {
        runTest {
            fetcher.clear(getCurrentUserId())
            session.logOut()
        }
    }

    @Test
    fun attachAndNotifyListeners() {
        var count = 0
        repeat(2) {
            fetcher.attach {
                count++
            }
        }
        runTest { fetcher.fetch() }
        assertEquals(2, count)
    }

    @Test
    fun detachListeners() {
        var isListenerAttached = false
        val listener = OnFetchListener { isListenerAttached = true }
        fetcher.attach(listener)
        fetcher.detach(listener)
        runTest { fetcher.fetch() }
        assertFalse(isListenerAttached)
    }

    @Test
    fun getActivities() {
        val expectedActivities = ContextualActivity.samples.toSerializableList()
        val fetchedActivities = fetcher.getActivities().unwrap()
        runTest {
            expectedActivities.forEach {
                activityRegistry.register(ownerUserId = getCurrentUserId(), it)
            }
            fetchedActivities.test {
                fetcher.fetch()
                assertEquals(getNamesOf(expectedActivities), getNamesOf(awaitItem()))
            }
        }
    }

    @Test
    fun getActivity() {
        runTest {
            val activityId = activityRegistry.register(
                ownerUserId = getCurrentUserId(),
                ContextualActivity.sample
            )
            fetcher.getActivity(activityId).unwrap().test {
                fetcher.fetch()
                assertEquals(ContextualActivity.sample.name, awaitItem().name)
            }
        }
    }

    @Test
    fun clear() {
        runTest {
            activityRegistry.register(getCurrentUserId(), ContextualActivity.samples)
            fetcher.clear(getCurrentUserId())
            fetcher.getActivities().unwrap().test {
                fetcher.fetch()
                assertEquals(emptySerializableList<ContextualActivity>(), awaitItem())
            }
        }
    }

    private suspend fun getCurrentUserId(): String {
        return session.getUser().filterNotNull().first().id
    }

    private fun getNamesOf(activities: List<ContextualActivity>): List<String> {
        return activities.map {
            it.name
        }
    }
}