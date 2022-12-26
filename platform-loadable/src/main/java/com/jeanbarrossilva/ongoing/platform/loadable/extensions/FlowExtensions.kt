package com.jeanbarrossilva.ongoing.platform.loadable.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import java.io.Serializable
import kotlin.experimental.ExperimentalTypeInference

/** Collects the given [Flow] as a [State] with [Loadable.Loading] as its initial value. **/
@Composable
fun <T: Serializable?> Flow<Loadable<T>>.collectAsState(): State<Loadable<T>> {
    return collectAsState(initial = Loadable.Loading())
}

/** Returns a [Flow] containing only [loaded][Loadable.Loaded] values. **/
fun <T: Serializable?> Flow<Loadable<T>>.filterIsLoaded(): Flow<Loadable.Loaded<T>> {
    return filterIsInstance()
}

fun <T: Serializable?> Flow<T>.loadable(): Flow<Loadable<T>> {
    return flow {
        map { Loadable.Loaded(it) }
            .onStart { emit(Loadable.Loading()) }
            .onCompletion { error -> error?.let { emit(Loadable.Failed(it)) } }
            .collect(::emit)
    }
}

/**
 * Unwraps [Loadable.Loaded] emissions and returns a [Flow] containing only their
 * [Loadable.Loaded.value]s.
 **/
fun <T: Serializable?> Flow<Loadable<T>>.unwrap(): Flow<T> {
    return filterIsLoaded().map {
        it.value
    }
}

/**
 * Creates a [Flow] through [channelFlow] with a [Loadable.Loading] as its initial value.
 *
 * @param block Production to be run whenever a terminal operator is applied to the resulting
 * [Flow].
 **/
@OptIn(ExperimentalTypeInference::class)
fun <T: Serializable?> loadableChannelFlow(
    @BuilderInference block: suspend ProducerScope<Loadable<T>>.() -> Unit
): Flow<Loadable<T>> {
    return channelFlow {
        send(Loadable.Loading())
        block()
    }
}