package com.jeanbarrossilva.ongoing.platform.loadable.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import java.io.Serializable

/**
 * Creates a [MutableStateFlow] with the given [initialValue], emitting it through the
 * [viewModelScope].
 *
 * @param initialValue [Loaded][Loadable.Loaded] value to be first held by the [MutableStateFlow].
 **/
@Suppress("KDocUnresolvedReference")
fun <T: Serializable> ViewModel.loadableFlowOf(origin: suspend () -> Flow<Loadable<T>>):
    MutableStateFlow<Loadable<T>> {
    return loadableFlowOf<T>().apply {
        viewModelScope.launch {
            origin().collect(::emit)
        }
    }
}

/**
 * Creates a [MutableStateFlow] with the given [initialValue] that gets all of [origin]'s values
 * emitted to it through the [viewModelScope].
 *
 * @param initialValue [Loaded][Loadable.Loaded] value to be first held by the [MutableStateFlow].
 * @param origin [Flow] that's the source of the emitted values.
 **/
@Suppress("KDocUnresolvedReference")
fun <T: Serializable> ViewModel.loadableFlowOf(
    initialValue: T,
    origin: suspend () -> Flow<Loadable<T>>
): MutableStateFlow<Loadable<T>> {
    return loadableFlowOf(initialValue).apply {
        viewModelScope.launch {
            emitAll(origin())
        }
    }
}

/**
 * Creates a [MutableStateFlow] with the given [initialValue] that [unwraps][unwrap] the
 * [origin] and transfers its emissions through the [viewModelScope].
 *
 * @param initialValue [Loaded][Loadable.Loaded] value to be first held by the [StateFlow].
 * @param origin [Flow] that's the source of the emitted values.
 **/
@Suppress("KDocUnresolvedReference")
fun <T: Serializable?> ViewModel.flowOf(initialValue: T, origin: suspend () -> Flow<Loadable<T>>):
    MutableStateFlow<T> {
    return MutableStateFlow(initialValue).apply {
        viewModelScope.launch {
            origin().unwrap().collect(::emit)
        }
    }
}