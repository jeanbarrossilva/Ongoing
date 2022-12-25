package com.jeanbarrossilva.ongoing.feature.activitydetails.infra

import com.jeanbarrossilva.ongoing.feature.activitydetails.domain.ContextActivity
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable

internal interface ActivityDetailsGateway {
    suspend fun getActivity(): Loadable<ContextActivity>

    suspend fun fetch()

    suspend fun setObserving(isObserving: Boolean)
}