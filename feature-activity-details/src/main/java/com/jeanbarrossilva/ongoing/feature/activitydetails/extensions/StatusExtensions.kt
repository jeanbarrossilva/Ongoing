package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualStatus
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.feature.activitydetails.domain.ContextStatus
import com.jeanbarrossilva.ongoing.feature.activitydetails.utils.ContextStatusUtils

/** Converts the given [Status] into a [ContextStatus]. **/
internal fun Status.toContextStatus(context: Context): ContextStatus {
    val titleRes = toContextualStatus().titleRes
    val title = context.getString(titleRes)
    return ContextStatus(title, ContextStatusUtils.changeDate)
}