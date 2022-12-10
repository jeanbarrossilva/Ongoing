package com.jeanbarrossilva.ongoing.context.registry.extensions

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf

/**
 * Applies the given [transform] to the [State].
 *
 * @param transform Transformation to be done to the [State.value].
 **/
internal fun <I, O> State<I>.map(transform: (I) -> O): State<O> {
    return derivedStateOf {
        transform(value)
    }
}