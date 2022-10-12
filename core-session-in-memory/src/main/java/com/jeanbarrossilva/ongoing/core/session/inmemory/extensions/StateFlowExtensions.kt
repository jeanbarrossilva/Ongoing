package com.jeanbarrossilva.ongoing.core.session.inmemory.extensions

import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KProperty

internal operator fun <T> StateFlow<T>.getValue(thisRef: Any?, property: KProperty<*>): T {
    return value
}