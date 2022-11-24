package com.jeanbarrossilva.ongoing.platform.registry.extensions

internal fun <T> MutableList<T>.addOrReplaceBy(replacement: T, predicate: (T) -> Boolean) {
    val iterator = iterator()
    var isReplacementAdded = false
    while (!isReplacementAdded && iterator.hasNext()) {
        val element = iterator.next()
        val index = indexOf(element)
        if (predicate(element)) {
            iterator.remove()
            add(index, replacement)
            isReplacementAdded = true
        }
    }
    if (!isReplacementAdded) {
        add(replacement)
    }
}