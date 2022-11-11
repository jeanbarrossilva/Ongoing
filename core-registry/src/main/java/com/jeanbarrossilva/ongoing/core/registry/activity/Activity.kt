package com.jeanbarrossilva.ongoing.core.registry.activity

import com.jeanbarrossilva.ongoing.core.registry.OnStatusChangeListener

data class Activity(
    val id: String,
    val ownerUserId: String?,
    val name: String,
    val icon: Icon
) {
    var statuses = Status.default
        private set(value) {
            value.ifEmpty { return }
            field = value
        }

    val status
        get() = statuses.last()

    constructor(id: String, ownerUserId: String?, name: String, icon: Icon, statuses: List<Status>):
        this(id, ownerUserId, name, icon) {
        this.statuses = statuses
    }

    abstract class Recorder {
        abstract suspend fun ownerUserId(id: String, ownerUserId: String?)

        abstract suspend fun name(id: String, name: String)

        abstract suspend fun icon(id: String, icon: Icon)

        abstract suspend fun status(id: String, status: Status)

        abstract suspend fun doOnStatusChange(listener: OnStatusChangeListener)
    }
}