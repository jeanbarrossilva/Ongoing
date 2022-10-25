package com.jeanbarrossilva.ongoing.core.registry.inmemory.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal fun <T> Flow<Collection<T>>.flatten(): Flow<T> {
    return flow {
        collect {
            it.forEach { element ->
                emit(element)
            }
        }
    }
}