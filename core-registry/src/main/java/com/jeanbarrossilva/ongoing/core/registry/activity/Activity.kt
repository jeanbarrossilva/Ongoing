package com.jeanbarrossilva.ongoing.core.registry.activity

import com.jeanbarrossilva.ongoing.core.registry.observation.Change
import com.jeanbarrossilva.ongoing.core.registry.observation.Observation

data class Activity(
    val id: String,
    val ownerUserId: String,
    val name: String,
    val icon: Icon,
    val observerUserIds: List<String>
) {
    var statuses = Status.default
        private set(value) {
            value.ifEmpty { return }
            field = value
        }

    var status
        get() = statuses.last()
        set(value) { statuses = statuses + value }

    constructor(
        id: String,
        ownerUserId: String,
        name: String,
        icon: Icon,
        statuses: List<Status>,
        observerUserIds: List<String>
    ): this(id, ownerUserId, name, icon, observerUserIds) {
        this.statuses = statuses
    }

    interface Observer {
        suspend fun attach(userId: String, activityId: String, observation: Observation)

        suspend fun notify(changerUserId: String?, activityId: String, change: Change)

        suspend fun detach(userId: String, activityId: String)

        suspend fun clear()
    }
}