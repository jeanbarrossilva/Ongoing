package com.jeanbarrossilva.ongoing.platform.extensions.identification

fun interface ApplicationIdProvider {
    fun provide(): String
}