package com.jeanbarrossilva.ongoing.feature.activities

import app.cash.turbine.test
import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import com.jeanbarrossilva.ongoing.core.session.inmemory.InMemorySessionManager
import com.jeanbarrossilva.ongoing.core.user.User
import com.jeanbarrossilva.ongoing.core.user.inmemory.InMemoryUserRepository
import com.jeanbarrossilva.ongoing.feature.activities.extensions.getUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SessionManagerExtensionsTests {
    private val userRepository = InMemoryUserRepository()
    private val sessionManager = InMemorySessionManager()

    @Before
    fun setUp() {
        runTest {
            sessionManager.session<Session.SignedOut>()?.start()
        }
    }

    @After
    fun tearDown() {
        runTest {
            sessionManager.session<Session.SignedIn>()?.end()
        }
    }

    @Test
    fun `GIVEN a started session WHEN getting the user THEN it is available`() {
        runTest {
            sessionManager.getUser(userRepository).test {
                sessionManager.session<Session.SignedIn>()?.end()
                awaitItem()
                sessionManager.session<Session.SignedOut>()?.start()
                assertEquals(User.sample, awaitItem())
            }
        }
    }

    @Test
    fun `GIVEN an ended session WHEN getting the user THEN it is null`() {
        runTest {
            sessionManager.getUser(userRepository).test {
                sessionManager.session<Session.SignedIn>()?.end()
                assertNull(awaitItem())
            }
        }
    }
}