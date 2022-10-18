package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Status
import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualStatus

fun Status.toContextualStatus(): ContextualStatus {
    return when (this) {
        Status.TO_DO -> ContextualStatus.TO_DO
        Status.ONGOING -> ContextualStatus.ONGOING
        Status.DONE -> ContextualStatus.DONE
    }
}