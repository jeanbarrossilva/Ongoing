package com.jeanbarrossilva.ongoing.core.registry.activity

import com.jeanbarrossilva.ongoing.core.registry.OnStatusChangeListener

data class Activity(
    val id: String,
    val ownerUserId: String,
    val name: String,
    val icon: Icon,
    val statuses: List<Status>
) {
    val status
        get() = statuses.last()

    abstract class Recorder {
        abstract suspend fun name(id: String, name: String)

        abstract suspend fun icon(id: String, icon: Icon)

        abstract suspend fun status(id: String, status: Status)

        abstract suspend fun doOnStatusChange(listener: OnStatusChangeListener)
    }
}