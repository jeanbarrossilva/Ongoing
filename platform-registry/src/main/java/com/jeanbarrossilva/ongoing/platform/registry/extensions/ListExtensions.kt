package com.jeanbarrossilva.ongoing.platform.registry.extensions

internal fun <T> List<T>.plusOrReplacingBy(replacement: T, predicate: (T) -> Boolean): List<T> {
    return toMutableList().apply { addOrReplaceBy(replacement, predicate) }.toList()
}