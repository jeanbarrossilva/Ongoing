package com.jeanbarrossilva.ongoing.feature.activities.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.OnFetchListener
import com.jeanbarrossilva.ongoing.platform.loadable.Loadable
import com.jeanbarrossilva.ongoing.platform.loadable.extensions.toSerializableList
import com.jeanbarrossilva.ongoing.platform.loadable.type.SerializableList

/**
 * Gets the given [ContextualActivitiesFetcher]'s up-to-date [Loadable][Loadable] [SerializableList] of
 * [contextual activities][ContextualActivity] wrapped in a [State].
 **/
@Composable
internal fun ContextualActivitiesFetcher.getActivitiesAsState():
    State<Loadable<SerializableList<ContextualActivity>>> {
    val state = remember {
        mutableStateOf<Loadable<SerializableList<ContextualActivity>>>(Loadable.Loading())
    }
    val listener = remember {
        OnFetchListener {
            state.value = Loadable.Loaded(it.toSerializableList())
        }
    }

    DisposableEffect(Unit) {
        attach(listener)
        onDispose { detach(listener) }
    }

    return state
}