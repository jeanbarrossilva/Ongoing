package com.jeanbarrossilva.ongoing.feature.activities.extensions

import java.util.UUID

internal fun uuid(): String {
    return UUID.randomUUID().toString()
}