package com.jeanbarrossilva.ongoing.core.registry.observation

fun interface Observation {
    fun onChange(change: Change)
}