package com.jeanbarrossilva.ongoing.platform.registry.test.session

import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.extensions.session
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.rules.ExternalResource

@OptIn(ExperimentalCoroutinesApi::class)
internal class SessionManagerRule(val sessionManager: SessionManager): ExternalResource() {
    override fun before() {
        runTest {
            sessionManager.session<Session.SignedOut>()?.start()
        }
    }

    override fun after() {
        runTest {
            sessionManager.session<Session.SignedIn>()?.end()
        }
    }
}