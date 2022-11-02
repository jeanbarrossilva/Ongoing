package com.jeanbarrossilva.ongoing.feature.activityediting

import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualStatus

internal data class ActivityEditingProps(val name: String, val currentStatus: ContextualStatus?) {
    constructor(activity: ContextualActivity): this(activity.name, activity.status)

    companion object {
        val empty = ActivityEditingProps(name = "", currentStatus = null)
    }
}