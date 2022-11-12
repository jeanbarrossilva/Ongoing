package com.jeanbarrossilva.ongoing.platform.loadable.extensions

import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList

inline fun <reified T> Collection<T>.toSerializableList(): SerializableList<T> {
    return serializableListOf(*toTypedArray())
}