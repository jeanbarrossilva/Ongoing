package com.jeanbarrossilva.ongoing.feature.activityediting

internal object ActivityEditingModel {
    fun isNameValid(name: String?): Boolean {
        return !name.isNullOrBlank()
    }
}