package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualIcon

fun Icon.toContextualIcon(): ContextualIcon {
    return when (this) {
        Icon.BOOK -> ContextualIcon.BOOK
        Icon.OTHER -> ContextualIcon.OTHER
    }
}