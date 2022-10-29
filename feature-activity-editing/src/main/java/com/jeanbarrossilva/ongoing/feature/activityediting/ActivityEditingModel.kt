package com.jeanbarrossilva.ongoing.feature.activityediting

import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualStatus

internal object ActivityEditingModel {
    fun isNameValid(name: String): Boolean {
        return name.isNotBlank()
    }

    fun isCurrentStatusValid(currentStatus: ContextualStatus?): Boolean {
        return currentStatus != null
    }
}