package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.extensions.replaceBy

internal fun <T> MutableList<T>.addOrReplaceBy(replacement: T, predicate: (T) -> Boolean) {
    val isNotReplaced = !replaceBy({ replacement }, predicate)
    if (isNotReplaced) {
        add(replacement)
    }
}