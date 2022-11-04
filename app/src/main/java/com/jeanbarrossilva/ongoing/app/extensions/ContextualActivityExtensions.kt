package com.jeanbarrossilva.ongoing.app.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.feature.activityediting.ActivityEditingMode

internal fun ContextualActivity?.toActivityEditingMode(): ActivityEditingMode {
    return this?.let { ActivityEditingMode.Modification(it) } ?: ActivityEditingMode.Addition
}