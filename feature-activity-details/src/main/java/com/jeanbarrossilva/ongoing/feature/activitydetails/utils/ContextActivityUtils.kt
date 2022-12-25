package com.jeanbarrossilva.ongoing.feature.activitydetails.utils

import com.jeanbarrossilva.ongoing.core.session.Session
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.core.session.extensions.session

internal object ContextActivityUtils {
    fun isBeingObserved(sessionManager: SessionManager, observerUserIds: List<String>): Boolean {
        return getCurrentUserId(sessionManager) in observerUserIds
    }

    fun isEditable(sessionManager: SessionManager, ownerUserId: String): Boolean {
        return getCurrentUserId(sessionManager) == ownerUserId
    }

    private fun getCurrentUserId(sessionManager: SessionManager): String? {
        return sessionManager.session<Session.SignedIn>()?.userId
    }
}