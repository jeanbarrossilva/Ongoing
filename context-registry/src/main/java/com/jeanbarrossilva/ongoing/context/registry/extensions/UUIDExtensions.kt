package com.jeanbarrossilva.ongoing.context.registry.extensions

import java.util.UUID

internal fun uuid(): String {
    return UUID.randomUUID().toString()
}