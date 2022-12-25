package com.jeanbarrossilva.ongoing.feature.activitydetails.domain

import java.io.Serializable

internal data class ContextActivity(
    val id: String,
    val iconName: String,
    val name: String,
    val statuses: List<ContextStatus>,
    val isEditable: Boolean,
    val isBeingObserved: Boolean
): Serializable {
    companion object
}