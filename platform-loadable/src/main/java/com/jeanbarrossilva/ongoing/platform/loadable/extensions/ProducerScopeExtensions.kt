package com.jeanbarrossilva.ongoing.platform.loadable.extensions

import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import kotlinx.coroutines.channels.ProducerScope
import java.io.Serializable

/**
 * Sends the given [element] as a [Loadable.Loaded].
 *
 * @param element Element to be sent.
 **/
suspend fun <T: Serializable?> ProducerScope<Loadable<T>>.send(element: T) {
    send(Loadable.Loaded(element))
}