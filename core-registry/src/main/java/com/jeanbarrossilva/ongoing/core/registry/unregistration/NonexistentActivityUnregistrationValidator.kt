package com.jeanbarrossilva.ongoing.core.registry.unregistration

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistryUnregistrationValidator
import com.jeanbarrossilva.ongoing.core.registry.NonexistentActivityException

internal class NonexistentActivityUnregistrationValidator(
    private val activityRegistry: ActivityRegistry,
    override val next: ActivityRegistryUnregistrationValidator?
): ActivityRegistryUnregistrationValidator() {
    override suspend fun validate(userId: String, activityId: String) {
        val activity = activityRegistry.getActivityById(activityId)
        if (activity == null) {
            throw NonexistentActivityException(activityId)
        } else {
            next?.validate(userId, activityId)
        }
    }
}