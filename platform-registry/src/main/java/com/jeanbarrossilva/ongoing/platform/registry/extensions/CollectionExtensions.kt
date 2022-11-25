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
 * @param predicate Determines whether or not the element that's being received should replace the
 * current one instead of getting appended.
 * @param transform Conversion of the currently iterated element into a [Flow].
 **/
internal fun <I, O> Collection<I>.joinToFlow(
    predicate: (current: O, replacement: O) -> Boolean,
    transform: (I) -> Flow<O>
): Flow<List<O>> {
    return if (isNotEmpty()) joinToFlowOrSuspendIndefinitely(predicate, transform) else emptyFlow()
}

internal fun Collection<ActivityEntity>.mapToActivity(
    statusDao: StatusDao,
    observerDao: ObserverDao
): Flow<List<Activity>> {
    return joinToFlow({ current, replacement -> current.id == replacement.id }) {
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
 * @param predicate Determines whether or not the element that's being received should replace the
 * current one instead of getting appended.
 * @param transform Conversion of the currently iterated element into a [Flow].
 **/
private fun <I, O> Collection<I>.joinToFlowOrSuspendIndefinitely(
    predicate: (current: O, replacement: O) -> Boolean,
    transform: (I) -> Flow<O>
): Flow<List<O>> {
    val elements = mutableListOf<O>()
    val isComplete = { elements.size == size }
    return channelFlow {
        suspendCoroutine { continuation ->
            var isResumed = false
            launch {
                map(transform).merge().collect { element ->
                    elements.addOrReplaceBy(element) {
                        predicate(element, it)
                    }
                    if (isComplete()) {
                        send(elements)
                        if (!isResumed) {
                            continuation.resume(Unit)
                            isResumed = true
                        }
                    }
                }
            }
        }
    }
}