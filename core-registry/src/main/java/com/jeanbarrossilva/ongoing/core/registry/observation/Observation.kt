package com.jeanbarrossilva.ongoing.core.registry.observation

fun interface Observation {
    suspend fun onChange(change: Change, activityId: String)
}