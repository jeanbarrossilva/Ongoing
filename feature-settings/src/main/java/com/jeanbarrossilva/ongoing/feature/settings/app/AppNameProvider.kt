package com.jeanbarrossilva.ongoing.feature.settings.app

fun interface AppNameProvider {
    fun provide(): String

    companion object {
        internal val sample = AppNameProvider {
            "Ongoing"
        }
    }
}