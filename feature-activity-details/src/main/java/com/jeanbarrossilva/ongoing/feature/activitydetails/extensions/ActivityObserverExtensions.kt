package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation
import com.jeanbarrossilva.ongoing.core.session.user.User

internal suspend fun Activity.Observer.toggle(
    user: User,
    activity: ContextualActivity,
    isObserving: Boolean,
    observation: Observation
) {
    if (isObserving) attach(user.id, activity.id, observation) else detach(user.id, activity.id)
}