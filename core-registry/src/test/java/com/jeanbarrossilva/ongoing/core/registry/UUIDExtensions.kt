package com.jeanbarrossilva.ongoing.core.registry

import java.util.UUID

internal fun uuid(): String {
    return UUID.randomUUID().toString()
}