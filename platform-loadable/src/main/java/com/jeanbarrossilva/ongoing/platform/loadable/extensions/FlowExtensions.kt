package com.jeanbarrossilva.ongoing.platform.loadable.extensions

import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.io.Serializable

fun <T: Serializable> Flow<Loadable<T>>.filterIsLoaded(): Flow<Loadable.Loaded<T>> {
    return filterIsInstance()
}

fun <T: Serializable> Flow<T>.loadable(): Flow<Loadable<T>> {
    return flow {
        map { Loadable.Loaded(it) }.onStart { emit(Loadable.Loading()) }.collect(::emit)
    }
}