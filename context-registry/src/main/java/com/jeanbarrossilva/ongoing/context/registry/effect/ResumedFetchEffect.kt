package com.jeanbarrossilva.ongoing.context.registry.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.platform.designsystem.effect.LifecycleEventEffect
import kotlinx.coroutines.launch

/**
 * Fetches [contextual activities][ContextualActivity] through the given [fetcher] whenever the
 * current [Lifecycle.Event] is [Lifecycle.Event.ON_RESUME].
 *
 * @param fetcher [ContextualActivitiesFetcher] to fetch the
 * [contextual activities][ContextualActivity] with.
 **/
@Composable
fun ResumedFetchEffect(fetcher: ContextualActivitiesFetcher) {
    val coroutineScope = rememberCoroutineScope()

    LifecycleEventEffect { _, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            coroutineScope.launch {
                fetcher.fetch()
            }
        }
    }
}