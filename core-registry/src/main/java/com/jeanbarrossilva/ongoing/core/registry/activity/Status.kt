package com.jeanbarrossilva.ongoing.core.registry.activity

@Suppress("Unused")
enum class Status {
    TO_DO,
    ONGOING,
    DONE;

    companion object {
        val all
            get() = values().toList()
    }
}