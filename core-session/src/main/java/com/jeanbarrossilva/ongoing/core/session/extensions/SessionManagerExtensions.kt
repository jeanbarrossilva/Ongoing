package com.jeanbarrossilva.ongoing.core.session.extensions

import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager

/**
 * Gets the [Session] that's the current one as a [T]. If the cast doesn't succeed, returns
 * `null`.
 **/
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified T: Session> SessionManager.session(): T? {
    val session = session()
    return if (session is T) session else null
}