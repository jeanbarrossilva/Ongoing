package com.jeanbarrossilva.ongoing.feature.activityediting.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.status.Status
import com.jeanbarrossilva.ongoing.feature.activityediting.domain.EditingStatus

/** Converts the given [EditingStatus] into a [Status]. **/
internal fun EditingStatus.toStatus(): Status {
    return Status[id]
}