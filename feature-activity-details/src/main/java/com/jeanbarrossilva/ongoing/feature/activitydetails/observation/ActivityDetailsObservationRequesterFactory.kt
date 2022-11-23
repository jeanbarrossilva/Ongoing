package com.jeanbarrossilva.ongoing.feature.activitydetails.observation

import com.jeanbarrossilva.ongoing.feature.activitydetails.observation.requester.AcceptObservationRequester
import com.jeanbarrossilva.ongoing.feature.activitydetails.observation.requester.AskForPermissionToSendNotificationsObservationRequester
import com.jeanbarrossilva.ongoing.feature.activitydetails.observation.requester.NavigateToNotificationsSettingsObservationRequester

internal object ActivityDetailsObservationRequesterFactory {
    fun create(): ActivityDetailsObservationRequester {
        val acceptObservationRequester = AcceptObservationRequester(null)
        val navigateToNotificationsSettings =
            NavigateToNotificationsSettingsObservationRequester(acceptObservationRequester)
        return AskForPermissionToSendNotificationsObservationRequester(
            navigateToNotificationsSettings
        )
    }
}