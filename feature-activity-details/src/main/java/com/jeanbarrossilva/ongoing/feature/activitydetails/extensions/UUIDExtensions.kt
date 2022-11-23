package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

import java.util.UUID

internal fun uuid(): String {
    return UUID.randomUUID().toString()
}