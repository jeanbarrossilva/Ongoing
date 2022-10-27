package com.jeanbarrossilva.ongoing.feature.activityediting

import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualStatus

internal object ActivityEditingModel {
    fun canSave(name: String, currentStatus: ContextualStatus?): Boolean {
        return name.isNotBlank() && currentStatus != null
    }
}