package com.jeanbarrossilva.ongoing.feature.activityediting.extensions

import android.content.Context
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingStatus

/**
 * Converts the given [ContextualStatus] into an [EditingStatus].
 *
 * @param context [Context] through which the [EditingStatus.title] will be obtained.
 **/
internal fun ContextualStatus.toEditingStatus(context: Context): EditingStatus {
    val title = context.getString(titleRes)
    return EditingStatus(name, title)
}