package com.jeanbarrossilva.ongoing.platform.registry.test.session

import com.jeanbarrossilva.ongoing.core.session.Session
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.rules.ExternalResource

@OptIn(ExperimentalCoroutinesApi::class)
internal class SessionRule(val session: Session): ExternalResource() {
    override fun before() {
        runTest {
            session.logIn()
        }
    }

    override fun after() {
        runTest {
            session.logOut()
        }
    }
}