package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.observation.ContextualChange
import com.jeanbarrossilva.ongoing.core.registry.observation.Change

fun Change.contextualize(): ContextualChange {
    return when (this) {
        is Change.Name -> ContextualChange.Name(old, new)
        is Change.Status -> contextualize()
    }
}