package com.jeanbarrossilva.ongoing.app.extensions

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.ContextualActivitiesFetcher
import com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher.OnFetchListener

/**
 * [Attaches][ContextualActivitiesFetcher.attach] the given [listener] and then
 * [detaches][ContextualActivitiesFetcher.detach] it after its [OnFetchListener.onRefresh] callback
 * has been ran.
 *
 * @param listener [OnFetchListener] to be temporarily attached.
 **/
internal fun ContextualActivitiesFetcher.attachUntilNextFetch(listener: OnFetchListener) {
    val selfDetachingListener = object: OnFetchListener {
        override suspend fun onRefresh(activities: List<ContextualActivity>) {
            listener.onRefresh(activities)
            detach(this)
        }
    }
    attach(selfDetachingListener)
}