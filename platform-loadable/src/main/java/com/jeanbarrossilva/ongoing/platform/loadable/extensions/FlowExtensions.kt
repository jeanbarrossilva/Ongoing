package com.jeanbarrossilva.ongoing.platform.loadable.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import java.io.Serializable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlin.experimental.ExperimentalTypeInference

@Composable
fun <T: Serializable> Flow<Loadable<T>>.collectAsState(): State<Loadable<T>> {
    return collectAsState(initial = Loadable.Loading())
}

@OptIn(ExperimentalTypeInference::class)
fun <T: Serializable> loadableFlow(
    @BuilderInference block: suspend FlowCollector<Loadable<T>>.() -> Unit
): Flow<Loadable<T>> {
    return flow {
        emit(Loadable.Loading())
        block()
    }
}