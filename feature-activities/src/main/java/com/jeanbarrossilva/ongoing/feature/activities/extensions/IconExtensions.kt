package com.jeanbarrossilva.ongoing.feature.activities.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Icon
import com.jeanbarrossilva.ongoing.feature.activities.context.ContextualIcon

internal fun Icon.toContextualIcon(): ContextualIcon {
    return when (this) {
        Icon.BOOK -> ContextualIcon.BOOK
        Icon.OTHER -> ContextualIcon.OTHER
    }
}