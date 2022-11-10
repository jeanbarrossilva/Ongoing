package com.jeanbarrossilva.ongoing.core.session.inmemory

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
internal class InMemorySessionManagerTests {
    private val sessionManager = InMemorySessionManager()

    @BeforeTest
    fun setUp() {
        runTest {
            sessionManager.logOut()
        }
    }

    @Test
    fun `GIVEN a non-authenticated user WHEN authenticating them THEN the they're logged in`() {
        runTest {
            sessionManager.logIn()
            assertEquals(sessionManager.getUser().first(), InMemorySessionManager.user)
        }
    }

    @Test
    fun `GIVEN a logged user WHEN logging them out THEN it's logged out`() {
        runTest {
            sessionManager.logIn()
            sessionManager.logOut()
            assertNull(sessionManager.getUser().first())
        }
    }
}