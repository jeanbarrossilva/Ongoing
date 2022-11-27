package com.jeanbarrossilva.ongoing.core.registry

internal abstract class ActivityRegistryUnregistrationValidator {
    protected abstract val next: ActivityRegistryUnregistrationValidator?

    abstract suspend fun validate(userId: String, activityId: String)
}