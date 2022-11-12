package com.jeanbarrossilva.ongoing.platform.loadable.type

import java.io.Serializable

data class SerializableList<T> internal constructor(private val elements: List<T>):
    List<T> by elements, Serializable