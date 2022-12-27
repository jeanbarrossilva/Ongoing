package com.jeanbarrossilva.ongoing.feature.activityediting.extensions

import android.content.Context
import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingStatus

/**
 * Converts the given [Activity] into an [EditingActivity].
 *
 * @param context [Context] through which the [Status] will be converted into an
 * [EditingStatus].
 **/
internal fun Activity.toEditingActivity(context: Context): EditingActivity {
    val status = status.toEditingStatus(context)
    return EditingActivity(name, status)
}