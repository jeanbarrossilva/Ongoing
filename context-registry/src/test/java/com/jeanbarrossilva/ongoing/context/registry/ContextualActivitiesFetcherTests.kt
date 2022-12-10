package com.jeanbarrossilva.ongoing.context.registry

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.OnFetchListener
import com.jeanbarrossilva.ongoing.core.registry.inmemory.InMemoryActivityRegistry
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySession
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemoryUserRepository
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
            activityRegistry.clear(getCurrentUserId())
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

    private suspend fun getCurrentUserId(): String {
        return session.getUser().filterNotNull().first().id
    }
}