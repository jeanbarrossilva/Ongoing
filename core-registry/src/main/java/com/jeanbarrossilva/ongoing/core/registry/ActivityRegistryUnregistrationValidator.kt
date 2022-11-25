package com.jeanbarrossilva.ongoing.core.registry

internal interface ActivityRegistryUnregistrationValidator {
    val next: ActivityRegistryUnregistrationValidator?

    suspend fun validate(userId: String, activityId: String)
}