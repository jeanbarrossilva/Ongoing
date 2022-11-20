package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityEntity
import com.jeanbarrossilva.ongoing.platform.registry.observer.ObserverDao
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Joins the elements of the given [Collection] to a [Flow], applying the specified
 * [transformation][transform] to each of them.
 *
 * Returns an empty [Flow] if the [Collection] is empty.
 *
 * @param transform Conversion of the currently iterated element into a [Flow].
 **/
internal fun <I, O> Collection<I>.joinToFlow(transform: (I) -> Flow<O>): Flow<List<O>> {
    return if (isNotEmpty()) joinToFlowOrSuspendIndefinitely(transform) else emptyFlow()
}

internal fun Collection<ActivityEntity>.mapToActivity(
    statusDao: StatusDao,
    observerDao: ObserverDao
): Flow<List<Activity>> {
    return joinToFlow {
        it.toActivity(statusDao, observerDao)
    }
}

/**
 * Joins the elements of the given [Collection] to a [Flow], applying the specified
 * [transformation][transform] to each of them.
 *
 * Note, however, that the coroutine in which a terminal operator gets applied to the returned
 * [Flow] will be suspended indefinitely if the [Collection] is actually empty.
 *
 * @param transform Conversion of the currently iterated element into a [Flow].
 **/
private fun <I, O> Collection<I>.joinToFlowOrSuspendIndefinitely(transform: (I) -> Flow<O>):
    Flow<List<O>> {
    val values = mutableListOf<O>()
    val isComplete = { values.size == size }
    return channelFlow {
        suspendCoroutine { continuation ->
            launch {
                map(transform).merge().collect {
                    values += it
                    if (isComplete()) {
                        send(values)
                        continuation.resume(Unit)
                    }
                }
            }
        }
    }
}