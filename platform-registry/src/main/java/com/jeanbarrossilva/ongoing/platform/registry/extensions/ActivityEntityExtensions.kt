package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityEntity

internal fun ActivityEntity.toActivity(): Activity {
    return Activity("$id", ownerUserId, name, icon, Status.all, currentStatus)
}