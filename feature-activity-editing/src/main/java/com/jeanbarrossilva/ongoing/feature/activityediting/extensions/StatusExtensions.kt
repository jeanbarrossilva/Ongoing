package com.jeanbarrossilva.ongoing.feature.activityediting.extensions

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.extensions.toContextualStatus
import com.jeanbarrossilva.ongoing.core.registry.activity.status.Status
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingStatus

/**
 * Converts the given [Status] into an [EditingStatus].
 *
 * @param context [Context] through which the [EditingStatus.title] will be obtained.
 **/
internal fun Status.toEditingStatus(context: Context): EditingStatus {
    val titleRes = toContextualStatus().titleRes
    val title = context.getString(titleRes)
    return EditingStatus(name, title)
}