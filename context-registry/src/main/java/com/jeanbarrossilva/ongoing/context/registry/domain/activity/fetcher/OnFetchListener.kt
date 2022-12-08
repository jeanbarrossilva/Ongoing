package com.jeanbarrossilva.ongoing.context.registry.domain.activity.fetcher

import com.jeanbarrossilva.ongoing.context.registry.domain.activity.ContextualActivity

fun interface OnFetchListener {
    fun onRefresh(activities: List<ContextualActivity>)
}