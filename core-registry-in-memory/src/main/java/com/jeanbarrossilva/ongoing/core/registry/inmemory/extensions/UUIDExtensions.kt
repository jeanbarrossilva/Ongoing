package com.jeanbarrossilva.ongoing.core.registry.inmemory.extensions

import java.util.UUID

internal fun uuid(): String {
    return UUID.randomUUID().toString()
}