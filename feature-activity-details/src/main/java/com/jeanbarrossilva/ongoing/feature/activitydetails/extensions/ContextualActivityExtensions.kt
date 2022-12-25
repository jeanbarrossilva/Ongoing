package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus
import com.jeanbarrossilva.ongoing.context.user.ContextualUser
import com.jeanbarrossilva.ongoing.core.session.SessionManager
import com.jeanbarrossilva.ongoing.feature.activitydetails.domain.ContextActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.domain.ContextStatus
import com.jeanbarrossilva.ongoing.feature.activitydetails.utils.ContextActivityUtils

/**
 * Converts the given [ContextualActivity] into a [ContextActivity].
 *
 * @param sessionManager [SessionManager] through which the editability and the observation state
 * of the [ContextActivity] will be obtained.
 * @param context [Context] through which the [ContextualStatus]es are converted into
 * [ContextStatus]es.
 **/
internal fun ContextualActivity.toContextActivity(
    sessionManager: SessionManager,
    context: Context
): ContextActivity {
    val observerUserIds = observers.map(ContextualUser::id)
    val statuses = statuses.map { it.toContextStatus(context) }
    val isEditable = ContextActivityUtils.isEditable(sessionManager, owner.id)
    val isBeingObserved = ContextActivityUtils.isBeingObserved(sessionManager, observerUserIds)
    return ContextActivity(id, icon.name, name, statuses, isEditable, isBeingObserved)
}