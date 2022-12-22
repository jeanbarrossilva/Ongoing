package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation

internal suspend fun Activity.Observer.toggle(
    userId: String,
    activity: ContextualActivity,
    isObserving: Boolean,
    observation: Observation
) {
    if (isObserving) attach(userId, activity.id, observation) else detach(userId, activity.id)
}