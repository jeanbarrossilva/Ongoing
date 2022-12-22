package com.jeanbarrossilva.ongoing.core.session.inmemory

import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager

class InMemorySessionManager: SessionManager() {
    private var session: Session = SignedOutSession()

    inner class SignedInSession(override val userId: String): Session.SignedIn {
        override fun toString(): String {
            return "SignedInSession(userId=$userId)"
        }

        override suspend fun end() {
            session(SignedOutSession())
        }
    }

    inner class SignedOutSession: Session.SignedOut {
        override fun toString(): String {
            return "SignedOutSession()"
        }

        override suspend fun start() {
            session(SignedInSession(SAMPLE_USER_ID))
        }
    }

    override fun session(): Session {
        return session
    }

    override fun onSession(session: Session) {
        this.session = session
    }

    companion object {
        internal const val SAMPLE_USER_ID = "7bf0999c-d301-4b45-99a0-b85e60bdb4e0"
    }
}