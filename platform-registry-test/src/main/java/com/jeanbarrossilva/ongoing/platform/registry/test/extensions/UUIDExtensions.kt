package com.jeanbarrossilva.ongoing.platform.registry.test.extensions

import java.util.UUID

internal fun uuid(): String {
    return UUID.randomUUID().toString()
}