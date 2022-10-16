package com.jeanbarrossilva.ongoing.core.session.extensions

import java.util.UUID

internal fun uuid(): String {
    return UUID.randomUUID().toString()
}