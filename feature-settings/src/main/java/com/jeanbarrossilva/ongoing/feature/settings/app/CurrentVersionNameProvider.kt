package com.jeanbarrossilva.ongoing.feature.settings.app

fun interface CurrentVersionNameProvider {
    fun provide(): String

    companion object {
        internal val sample = CurrentVersionNameProvider {
            "1.0.0"
        }
    }
}