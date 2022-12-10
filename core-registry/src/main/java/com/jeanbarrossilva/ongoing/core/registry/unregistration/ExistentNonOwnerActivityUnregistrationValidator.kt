package com.jeanbarrossilva.ongoing.core.registry.unregistration

import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistry
import com.jeanbarrossilva.ongoing.core.registry.ActivityRegistryUnregistrationValidator
import kotlinx.coroutines.flow.first

internal class ExistentNonOwnerActivityUnregistrationValidator(
    private val activityRegistry: ActivityRegistry,
    override val next: ActivityRegistryUnregistrationValidator?
): ActivityRegistryUnregistrationValidator() {
    override suspend fun validate(userId: String, activityId: String) {
        val activity = activityRegistry.getActivityById(activityId)
        if (activity != null && userId != activity.ownerUserId) {
            throw IllegalArgumentException(
                "Only the owner of activity ${activity.id} can unregister it."
            )
        } else {
            next?.validate(userId, activityId)
        }
    }
}