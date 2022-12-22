package com.jeanbarrossilva.ongoing.core.session.inmemory

import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
internal class InMemorySessionManagerTests {
    private val sessionManager = InMemorySessionManager()

    @AfterTest
    fun tearDown() {
        runTest {
            sessionManager.session<Session.SignedIn>()?.end()
        }
    }

    @Test
    fun `GIVEN an unavailable session WHEN when starting it THEN it is available`() {
        runTest {
            sessionManager.session<Session.SignedOut>()?.start()
            assertEquals(
                sessionManager.session<Session.SignedIn>()?.userId,
                InMemorySessionManager.SAMPLE_USER_ID
            )
        }
    }

    @Test
    fun `GIVEN an available session WHEN ending it THEN it is unavailable`() {
        runTest {
            sessionManager.session<Session.SignedOut>()?.start()
            sessionManager.session<Session.SignedIn>()?.end()
            assertIs<Session.SignedOut>(sessionManager.session())
        }
    }
}