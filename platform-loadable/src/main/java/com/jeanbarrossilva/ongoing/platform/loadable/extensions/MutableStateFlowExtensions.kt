package com.jeanbarrossilva.ongoing.platform.loadable.extensions

import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.Serializable

/** Creates a [MutableStateFlow] with a [Loadable.Loading] as its initial value. **/
fun <T: Serializable?> loadableFlowOf(): MutableStateFlow<Loadable<T>> {
    return MutableStateFlow(Loadable.Loading())
}