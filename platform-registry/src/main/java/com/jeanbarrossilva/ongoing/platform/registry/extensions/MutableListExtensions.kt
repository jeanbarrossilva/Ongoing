package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.extensions.replaceBy

internal fun <T> MutableList<T>.addOrReplaceBy(replacement: T, predicate: (T) -> Boolean) {
    var isReplaced = false
    replaceBy(replacement) { element ->
        predicate(element).also {
            if (it) {
                isReplaced = true
            }
        }
    }
    if (!isReplaced) {
        add(replacement)
    }
}