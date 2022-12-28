package com.jeanbarrossilva.ongoing.core.registry.activity.status

enum class Status {
    TO_DO {
        override val id = "to-do"
    },
    ONGOING {
        override val id = "ongoing"
    },
    DONE {
        override val id = "done"
    };

    protected abstract val id: String

    companion object {
        val default = listOf(TO_DO)

        operator fun get(id: String): Status {
            return Status.values().find { it.id == id } ?: throw InvalidStatusException(id)
        }
    }
}