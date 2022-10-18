package com.jeanbarrossilva.ongoing.feature.activitydetails

import com.jeanbarrossilva.ongoing.context.registry.domain.ContextualStatus
import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.capitalized
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ActivityDetailsModel {
    fun getDisplayableCreationMomentOf(status: ContextualStatus): String {
        val now = LocalDateTime.now()
        return DateTimeFormatter.ofPattern("MMM dd, HH:mm").format(now).capitalized
    }
}