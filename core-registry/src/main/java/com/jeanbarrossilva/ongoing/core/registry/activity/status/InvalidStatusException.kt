package com.jeanbarrossilva.ongoing.core.registry.activity.status

internal class InvalidStatusException(statusId: String):
    IllegalArgumentException("\"$statusId\" is not a valid status ID.")