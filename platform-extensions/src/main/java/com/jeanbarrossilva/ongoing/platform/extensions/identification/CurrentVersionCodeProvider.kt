package com.jeanbarrossilva.ongoing.platform.extensions.identification

fun interface CurrentVersionCodeProvider {
    fun provide(): Int
}