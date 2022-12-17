package com.jeanbarrossilva.ongoing.feature.activityediting

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualStatus

internal data class ActivityEditingProps(val name: String, val currentStatus: ContextualStatus) {
    constructor(activity: ContextualActivity): this(activity.name, activity.status)

    companion object {
        val empty = ActivityEditingProps(name = "", ContextualStatus.TO_DO)
    }
}