package com.jeanbarrossilva.ongoing.feature.activitydetails.observation.requester

import com.jeanbarrossilva.ongoing.feature.activitydetails.ActivityDetailsActivity
import com.jeanbarrossilva.ongoing.feature.activitydetails.observation.ActivityDetailsObservationRequester

internal class AcceptObservationRequester(override val next: ActivityDetailsObservationRequester?):
    ActivityDetailsObservationRequester() {
    override fun request(
        activity: ActivityDetailsActivity,
        isObserving: Boolean,
        onAcceptance: () -> Unit
    ) {
        if (activity.areNotificationsEnabled) {
            onAcceptance()
        } else {
            next?.request(activity, isObserving, onAcceptance)
        }
    }
}