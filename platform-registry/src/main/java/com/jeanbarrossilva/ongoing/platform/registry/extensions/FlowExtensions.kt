package com.jeanbarrossilva.ongoing.platform.registry.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal fun <T> Flow<Flow<T>?>.flatten(): Flow<T?> {
    return flow {
        collect {
            it?.collect(::emit) ?: emit(null)
        }
    }
}