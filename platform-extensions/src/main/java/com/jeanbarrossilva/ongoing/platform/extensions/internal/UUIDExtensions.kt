package com.jeanbarrossilva.ongoing.platform.extensions.internal

import java.util.UUID

internal fun uuid(): String {
    return UUID.randomUUID().toString()
}