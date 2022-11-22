package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.observation.ContextualChange
import com.jeanbarrossilva.ongoing.core.registry.observation.Change

internal fun Change.Status.contextualize(): ContextualChange.Status {
    val old = old?.toContextualStatus()
    val new = new.toContextualStatus()
    return ContextualChange.Status(old, new)
}