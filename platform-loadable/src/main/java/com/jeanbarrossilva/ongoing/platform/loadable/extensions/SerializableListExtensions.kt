package com.jeanbarrossilva.ongoing.platform.loadable.extensions

import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList

fun <T> serializableListOf(vararg elements: T): SerializableList<T> {
    val elementsAsList = elements.toList()
    return SerializableList(elementsAsList)
}