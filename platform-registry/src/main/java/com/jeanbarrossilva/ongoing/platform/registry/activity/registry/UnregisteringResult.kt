package com.jeanbarrossilva.ongoing.platform.registry.activity.registry

internal sealed interface UnregisteringResult {
    object Allowed: UnregisteringResult

    object Denied: UnregisteringResult {
        class Exception(activityId: String): IllegalAccessException(
            "Only the owner of activity $activityId may execute this operation."
        )
    }

    object Nonexistent: UnregisteringResult {
        class Exception(activityId: String): IllegalArgumentException(
            "Activity $activityId does not exist."
        )
    }

    companion object {
        fun of(isActivityExistent: Boolean, isActivityOwner: Boolean): UnregisteringResult {
            return when {
                isActivityExistent && isActivityOwner -> Allowed
                isActivityExistent && !isActivityOwner -> Denied
                !isActivityExistent && isActivityOwner ->
                    throw IllegalStateException("Can't be the owner of a nonexistent activity.")
                else -> Nonexistent
            }
        }
    }
}