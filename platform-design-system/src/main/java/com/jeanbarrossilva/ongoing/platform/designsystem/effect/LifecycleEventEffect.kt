package com.jeanbarrossilva.ongoing.platform.designsystem.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

/**
 * Effect that attaches the given [observer] to the current [Lifecycle].
 *
 * @param observer [LifecycleEventObserver] to get notified of the current [Lifecycle]'s
 * [Lifecycle.Event] changes.
 **/
@Composable
fun LifecycleEventEffect(observer: LifecycleEventObserver) {
    LifecycleEventEffect(LocalLifecycleOwner.current.lifecycle, observer)
}

/**
 * Effect that attaches the given [observer] to [lifecycle].
 *
 * @param lifecycle [Lifecycle] to which [observer] will be attached.
 * @param observer [LifecycleEventObserver] to get notified of [lifecycle]'s [Lifecycle.Event]
 * changes.
 **/
@Composable
internal fun LifecycleEventEffect(lifecycle: Lifecycle, observer: LifecycleEventObserver) {
    var currentEvent by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    val currentEventChangeObserver = remember {
        LifecycleEventObserver { _, e ->
            currentEvent = e
        }
    }

    DisposableEffect(lifecycle) {
        lifecycle.addObserver(currentEventChangeObserver)
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
            lifecycle.removeObserver(currentEventChangeObserver)
        }
    }
}