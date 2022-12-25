package com.jeanbarrossilva.ongoing.feature.activitydetails.utils

import com.jeanbarrossilva.ongoing.feature.activitydetails.extensions.capitalized
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal object ContextStatusUtils {
    val changeDate
        get() = DateTimeFormatter.ofPattern("MMM dd, HH:mm").format(LocalDateTime.now()).capitalized
}