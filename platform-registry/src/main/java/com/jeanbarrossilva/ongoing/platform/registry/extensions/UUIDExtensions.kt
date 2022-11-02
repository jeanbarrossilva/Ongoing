package com.jeanbarrossilva.ongoing.platform.registry.extensions

import java.util.UUID

internal fun uuid(): String {
    return UUID.randomUUID().toString()
}