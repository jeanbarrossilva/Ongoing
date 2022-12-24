package com.jeanbarrossilva.ongoing.feature.activities

import com.jeanbarrossilva.ongoing.context.registry.extensions.toActivitiesOwner
import com.jeanbarrossilva.ongoing.context.user.ContextualUser
import java.io.Serializable

data class ActivitiesOwner(val id: String, val avatarUrl: String?): Serializable {
    companion object {
        val sample = ContextualUser.sample.toActivitiesOwner()
    }
}