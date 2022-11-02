package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusEntity

internal fun StatusEntity.toStatus(): Status {
    return Status.values.first {
        it.name == name
    }
}