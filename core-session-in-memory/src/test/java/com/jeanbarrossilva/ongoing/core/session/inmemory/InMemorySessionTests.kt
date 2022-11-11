package com.jeanbarrossilva.ongoing.core.session.inmemory

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
internal class InMemorySessionTests {
    private val session = InMemorySession()

    @BeforeTest
    fun setUp() {
        runTest {
            session.logOut()
        }
    }

    @Test
    fun `GIVEN a non-authenticated user WHEN authenticating them THEN the they're logged in`() {
        runTest {
            session.logIn()
            assertEquals(session.getUser().first(), InMemorySession.user)
        }
    }

    @Test
    fun `GIVEN a logged user WHEN logging them out THEN it's logged out`() {
        runTest {
            session.logIn()
            session.logOut()
            assertNull(session.getUser().first())
        }
    }
}