package com.jeanbarrossilva.ongoing.core.registry.activity

enum class Status {
    TO_DO,
    ONGOING,
    DONE;

    companion object {
        val values = values().toList()
        val default = listOf(TO_DO)
    }
}