package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation

internal suspend fun Activity.Observer.toggle(
    userId: String,
    activityId: String,
    isObserving: Boolean,
    observation: Observation
) {
    if (isObserving) attach(userId, activityId, observation) else detach(userId, activityId)
}