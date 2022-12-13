package com.jeanbarrossilva.ongoing.platform.registry.extensions

import com.jeanbarrossilva.ongoing.core.registry.activity.Activity
import com.jeanbarrossilva.ongoing.platform.registry.activity.ActivityEntity
import com.jeanbarrossilva.ongoing.platform.registry.observer.ObserverDao
import com.jeanbarrossilva.ongoing.platform.registry.status.StatusDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.scan

/**
 * Maps the elements of the given [Collection] to a [Flow], applying the specified
 * [transformation][transform] to each of them.
 *
 * The resulting [Flow] returns an empty [List] whenever it gets emitted to if the original
 * [Collection] is empty.
 *
 * @param predicate Determines whether or not the element that's being received should replace the
 * current one instead of getting appended.
 * @param transform Conversion of the currently iterated element to a [Flow].
 **/
internal fun <I, O> Collection<I>.map(
    predicate: (current: O, replacement: O) -> Boolean,
    transform: (I) -> Flow<O>
): Flow<List<O>> {
    return map(transform)
        .merge()
        .scan(emptyList<O>()) { accumulator, replacement ->
            accumulator.plusOrReplacingBy(replacement) { current ->
                predicate(current, replacement)
            }
        }
        .drop(size)
}

internal fun Collection<ActivityEntity>.mapToActivity(
    statusDao: StatusDao,
    observerDao: ObserverDao
): Flow<List<Activity>> {
    return map({ current, replacement -> current.id == replacement.id }) {
        it.toActivity(statusDao, observerDao)
    }
}