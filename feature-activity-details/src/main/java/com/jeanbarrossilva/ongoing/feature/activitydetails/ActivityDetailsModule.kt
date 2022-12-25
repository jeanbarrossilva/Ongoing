package com.jeanbarrossilva.ongoing.feature.activitydetails

import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.getActivityDetailsGateway
import org.koin.dsl.module

val activityDetailsModule = module {
    single {
        (activityId: String,
         androidActivity: ActivityDetailsActivity,
         onObservationToggle: (isObservation: Boolean) -> Unit)  ->
        getActivityDetailsGateway(activityId, androidActivity, onObservationToggle)
    }
}