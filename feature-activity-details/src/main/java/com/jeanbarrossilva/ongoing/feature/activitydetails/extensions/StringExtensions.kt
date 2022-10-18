package com.jeanbarrossilva.ongoing.feature.activitydetails.extensions

import java.util.Locale

internal val String.capitalized: String
    get() {
        val locale = Locale.getDefault()
        return replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
    }