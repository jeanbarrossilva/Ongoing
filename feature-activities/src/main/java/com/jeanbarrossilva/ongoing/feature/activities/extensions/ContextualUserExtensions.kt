package com.jeanbarrossilva.ongoing.context.registry.extensions

import com.jeanbarrossilva.ongoing.context.user.ContextualUser
import com.jeanbarrossilva.ongoing.feature.activities.ActivitiesOwner

/** Converts the given [ContextualUser] into an [ActivitiesOwner]. **/
internal fun ContextualUser.toActivitiesOwner(): ActivitiesOwner {
    return ActivitiesOwner(id, avatarUrl)
}