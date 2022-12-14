package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus
import com.jeanbarrossilva.ongoing.feature.activitydetails.domain.ContextStatus
import com.jeanbarrossilva.ongoing.feature.activitydetails.utils.ContextStatusUtils

/** Converts the given [ContextualStatus] into a [ContextStatus]. **/
internal fun ContextualStatus.toContextStatus(context: Context): ContextStatus {
    val title = context.getString(titleRes)
    return ContextStatus(title, ContextStatusUtils.changeDate)
}