package com.jeanbarrossilva.ongoing.platform.loadable.extensions

import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import java.io.Serializable

/**
 * Returns the result of the given [operation] that's ran on the [loaded][Loadable.Loaded] value;
 * if the [Loadable] is not a [Loadable.Loaded], returns `null`.
 *
 * @param operation Lambda whose result will get returned if this is a [Loadable.Loaded].
 **/
inline fun <I: Serializable?, O> Loadable<I>.ifLoaded(operation: I.() -> O): O? {
    return when (this) {
        is Loadable.Loading -> null
        is Loadable.Loaded -> value.operation()
    }
}

/**
 * Maps the current value to the [transformed][transform] one and wraps it in a [Loadable.Loaded] if
 * the receiver is of this same type; otherwise, returns a [Loadable.Loading].
 *
 * @param transform Transformation to be done to the [loaded][Loadable.Loaded] value.
 **/
inline fun <I: Serializable?, O: Serializable?> Loadable<I>.map(transform: (I) -> O): Loadable<O> {
    return when (this) {
        is Loadable.Loading -> Loadable.Loading()
        is Loadable.Loaded -> Loadable.Loaded(transform(value))
    }
}