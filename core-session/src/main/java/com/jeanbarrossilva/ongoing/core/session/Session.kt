package com.jeanbarrossilva.ongoing.core.session

sealed interface Session {
    interface SignedIn: Session {
        /** ID of the user that's authenticated. **/
        val userId: String

        /** Ends the current [Session]. **/
        suspend fun end()
    }

    interface SignedOut: Session {
        /** Starts a new [Session]. **/
        suspend fun start()
    }
}