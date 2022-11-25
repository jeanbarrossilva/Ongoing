package com.jeanbarrossilva.ongoing.core.extensions

fun <T> MutableList<T>.replaceBy(replacement: T.() -> T, predicate: (T) -> Boolean) {
    val iterator = iterator()
    var isReplaced = false
    while (!isReplaced && iterator.hasNext()) {
        val element = iterator.next()
        val index = indexOf(element)
        if (predicate(element)) {
            iterator.remove()
            add(index, element.replacement())
            isReplaced = true
        }
    }
}