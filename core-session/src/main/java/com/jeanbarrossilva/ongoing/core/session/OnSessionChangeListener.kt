package com.jeanbarrossilva.ongoing.core.session

fun interface OnSessionChangeListener {
    suspend fun onSessionChange(session: Session)
}