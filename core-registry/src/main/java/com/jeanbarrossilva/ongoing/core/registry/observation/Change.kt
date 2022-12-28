package com.jeanbarrossilva.ongoing.core.registry.observation

import com.jeanbarrossilva.ongoing.core.registry.activity.status.Status as _Status

sealed interface Change {
    data class Name(val old: String, val new: String): Change

    data class Status(val old: _Status?, val new: _Status): Change
}