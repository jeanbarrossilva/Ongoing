package com.jeanbarrossilva.ongoing.feature.activityediting.domain

import java.io.Serializable

internal data class EditingActivity(val name: String, val status: EditingStatus):
    Serializable {
    companion object {
        val sample = EditingActivity(name = "Create a new app", EditingStatus.sample)
    }
}