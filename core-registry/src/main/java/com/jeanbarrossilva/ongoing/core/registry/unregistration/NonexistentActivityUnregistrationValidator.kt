package com.jeanbarrossilva.ongoing.core.registry.unregistration

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistryUnregistrationValidator
import kotlinx.coroutines.flow.first

internal class NonexistentActivityUnregistrationValidator(
    private val activityRegistry: ActivityRegistry,
    override val next: ActivityRegistryUnregistrationValidator?
): ActivityRegistryUnregistrationValidator() {
    override suspend fun validate(userId: String, activityId: String) {
        val activity = activityRegistry.getActivityById(activityId).first()
        if (activity == null) {
            throw IllegalArgumentException("Activity $activityId does not exist.")
        } else {
            next?.validate(userId, activityId)
        }
    }
}