package com.jeanbarrossilva.ongoing.feature.activitydetails

import android.content.Context
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

object ActivityDetailsBridge {
    private var navigator: DestinationsNavigator? = null

    fun cross(context: Context, navigator: DestinationsNavigator, activityId: String) {
        this.navigator = navigator
        ActivityDetailsActivity.start(context, activityId)
    }

    internal fun getNavigator(): DestinationsNavigator {
        return requireNotNull(navigator)
    }

    internal fun clear() {
        navigator = null
    }
}