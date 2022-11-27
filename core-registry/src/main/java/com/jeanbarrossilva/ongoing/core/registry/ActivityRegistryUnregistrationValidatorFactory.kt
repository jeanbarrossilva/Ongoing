package com.jeanbarrossilva.ongoing.core.registry

import com.jeanbarrossilva.ongoing.core.registry.unregistration.ExistentNonOwnerActivityUnregistrationValidator
import com.jeanbarrossilva.ongoing.core.registry.unregistration.NonexistentActivityUnregistrationValidator

internal object ActivityRegistryUnregistrationValidatorFactory {
    fun create(activityRegistry: ActivityRegistry): ActivityRegistryUnregistrationValidator {
        val nonExistentActivity = NonexistentActivityUnregistrationValidator(activityRegistry, null)
        return ExistentNonOwnerActivityUnregistrationValidator(
            activityRegistry,
            nonExistentActivity
        )
    }
}