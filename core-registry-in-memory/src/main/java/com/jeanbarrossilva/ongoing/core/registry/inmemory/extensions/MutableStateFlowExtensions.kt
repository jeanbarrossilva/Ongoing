package com.jeanbarrossilva.ongoing.core.registry.inmemory.extensions

import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KProperty

internal operator fun <T> MutableStateFlow<T>.setValue(
    thisRef: Any?,
    property: KProperty<*>,
    value: T
) {
    this.value = value
}