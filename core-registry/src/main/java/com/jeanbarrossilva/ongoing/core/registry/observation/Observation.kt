package com.jeanbarrossilva.ongoing.core.registry.observation

fun interface Observation {
    suspend fun onChange(changerUserId: String?, activityId: String, change: Change)
}